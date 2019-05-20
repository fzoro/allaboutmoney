package zoret4.allaboutmoney.order.model.service.contracts

import org.bson.Document
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.configuration.unwrap
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.domain.OrderStatus
import zoret4.allaboutmoney.order.model.enums.VendorWebhookEvent
import zoret4.allaboutmoney.order.model.exception.OrderConfigurationException
import zoret4.allaboutmoney.order.model.repository.CustomerRepository
import zoret4.allaboutmoney.order.model.repository.OrderRepository
import zoret4.allaboutmoney.order.model.repository.PreferencesRepository
import zoret4.allaboutmoney.order.model.service.contracts.vendor.VendorOrderService
import zoret4.allaboutmoney.order.model.vendor.wirecard.service.WirecardOrderService

@Service
class OrderServiceImpl(private val props: AppProperties,
                       private val repo: OrderRepository,
                       private val customerRepository: CustomerRepository,
                       private val customerService: CustomerService,
                       private val vendorOrderService: VendorOrderService,
                       private val preferencesRepository: PreferencesRepository) : OrderService {

    companion object {
        val LOG = logger()
    }

    /**
     * @warning Whenever a new vendor is added, it might cause errors, since the customer might be posted to a vendor but not to other.
     */
    override fun create(order: Order): String {
        val dbCustomer = customerRepository.findById(order.customerId).unwrap() ?: throw ResourceNotFoundException()
        val vendorCustomerId = if (null == dbCustomer.vendor) customerService.postToVendor(dbCustomer) else dbCustomer.vendor?.get("id") as String
        val vendorOrder = order.payment.method.process(vendorCustomerId, order, vendorOrderService)
        val dbVendorOrder = Document.parse(vendorOrder)
        LOG.info("Order saved and posted to vendor. vendorOrder={}", vendorOrder)
        repo.save(order.copy(status = OrderStatus.CREATED, vendor = dbVendorOrder))
        val payCheckoutUri = ((((dbVendorOrder
                ["links"] as Map<*, *>)
                ["checkout"] as Map<*, *>)
                ["payCheckout"] as Map<*, *>)
                ["redirectHref"] as String)

        LOG.debug("Order posted. payCheckoutUri={}", payCheckoutUri)
        return payCheckoutUri
    }

    override fun get(id: String): Order {
        val dbOrder = repo.findById(id).unwrap() ?: throw ResourceNotFoundException()
        LOG.debug("get order by id={}. response={}", id, dbOrder)
        return dbOrder
    }

    override fun handleEvent(eventBody: Map<*, *>, authToken: String, event: VendorWebhookEvent) {
        if (!isTokenValid(authToken, event)) throw HttpClientErrorException(HttpStatus.UNAUTHORIZED)

        LOG.info("a new event has been received. event_name = $event ")

        val newStatus = when (event) {
            VendorWebhookEvent.PAYMENT -> vendorOrderService.handlePaymentEvent(eventBody)
            VendorWebhookEvent.ORDER -> vendorOrderService.handleOrderEvent(eventBody)
        }

        val wirecardOwnId = ((eventBody["resource"] as Map<*, *>)["order"] as Map<*, *>)["ownId"] as String
        val dbOrder = repo.findById(wirecardOwnId).unwrap() ?: throw ResourceNotFoundException()
        val newListOfEvents: MutableList<Map<*, *>> = mutableListOf()
        dbOrder.events?.let { newListOfEvents.addAll(it) }
        newListOfEvents.add(eventBody)

        LOG.info("updating order(id=$wirecardOwnId) database with newStatus=$newStatus and new list of Events")
        repo.save(dbOrder.copy(status = newStatus, events = newListOfEvents))

    }

    private fun isTokenValid(authToken: String, event: VendorWebhookEvent): Boolean {
        val dbPreferences = preferencesRepository.findByVendor_Name(props.upstream.wirecard.name)
                ?: throw OrderConfigurationException("Vendor WIRECARD not found on preferences configuration")
        val dbToken = dbPreferences.vendor.eventTokens[event.name]
                ?: throw OrderConfigurationException("Event: ${event.name} not found in database for WIRECARD")

        val result = dbToken == authToken

        if (!result) {
            WirecardOrderService.LOG.warn("dbToken != authToken. Either configuration is wrong or an unknown host tried to trigger a webhook event")
        }
        return result
    }
}

