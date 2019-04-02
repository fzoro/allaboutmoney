package zoret4.allaboutmoney.order.model.service.contracts

import org.bson.Document
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.unwrap
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.domain.OrderStatus
import zoret4.allaboutmoney.order.model.repository.CustomerRepository
import zoret4.allaboutmoney.order.model.repository.OrderRepository
import zoret4.allaboutmoney.order.model.service.contracts.vendor.VendorOrderService

@Service
class OrderServiceImpl(private val repo: OrderRepository,
                       private val customerRepository: CustomerRepository,
                       private val customerService: CustomerService,
                       private val vendorOrderService: VendorOrderService) : OrderService {
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
}

