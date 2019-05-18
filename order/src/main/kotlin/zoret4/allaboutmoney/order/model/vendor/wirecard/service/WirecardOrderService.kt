package zoret4.allaboutmoney.order.model.vendor.wirecard.service

import br.com.moip.API
import br.com.moip.request.CustomerRequest
import br.com.moip.request.OrderAmountRequest
import br.com.moip.request.OrderRequest
import br.com.moip.request.SubtotalsRequest
import br.com.moip.resource.Payment
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.configuration.toDigit
import zoret4.allaboutmoney.order.configuration.toJsonWithMapper
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.exception.OrderConfigurationException
import zoret4.allaboutmoney.order.model.repository.PreferencesRepository
import zoret4.allaboutmoney.order.model.service.contracts.vendor.VendorOrderService

@Service
class WirecardOrderService(private val props: AppProperties,
                           private val objectMapper: ObjectMapper,
                           private val api: API,
                           private val preferencesRepository: PreferencesRepository) : VendorOrderService {
    companion object {
        val LOG = logger()
    }

    override fun getOrder(orderId: String): String {
        LOG.debug("retrieving order from database. order.id={}", orderId)
        val wOrder = api.order().get(orderId) ?: throw ResourceNotFoundException()
        LOG.info("success on getting wirecard order by id={}", orderId)
        LOG.debug("wirecard.order={}", wOrder)
        return wOrder.toJsonWithMapper(objectMapper)
    }

    override fun checkoutByVendor(vendorCustomerId: String, order: Order): String {
        LOG.debug("checking out by vendor. vendorCustomerId={} / order={}", vendorCustomerId, order)
        val orderRequest: OrderRequest
        with(order) {
            orderRequest = OrderRequest()
                    .ownId(id)
                    .customer(CustomerRequest().id(vendorCustomerId))
                    .amount(OrderAmountRequest().currency(payment.currency.name)
                            .subtotals(SubtotalsRequest()
                                    .shipping(payment.shipping.toDigit())
                                    .addition(payment.addition.toDigit())
                                    .discount(payment.discount.toDigit())

                            )
                    )
            products.forEach { orderRequest.addItem(it.id, it.quantity, it.description, it.price.toDigit()) }
        }
        LOG.info("Posting OrderRequest to WireCard: query={}", orderRequest)
        val wOrder = api.order().create(orderRequest)
        LOG.debug("wirecard order created on their side. response={}", wOrder)
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wOrder)
    }

    private fun isTokenValid(authToken:String, eventName: String):Boolean{
        val dbPreferences = preferencesRepository.findByVendor_Name(props.upstream.wirecard.name) ?: throw OrderConfigurationException("Vendor WIRECARD not found on preferences configuration")
        val dbToken = dbPreferences.vendor.eventTokens[eventName] ?: throw OrderConfigurationException("Event: $eventName not found in database for WIRECARD")
        val result = dbToken == authToken

        if(!result){
            LOG.warn("dbToken != authToken. Either configuration is wrong or an unknown host tried to trigger a webhook event")
        }
        return result
    }

    override fun handlePaymentEvent(payment: Payment, authToken: String, eventName:String) {
        if(!isTokenValid(authToken, eventName)) throw HttpClientErrorException(HttpStatus.UNAUTHORIZED)
    }

    override fun handleOrderEvent(order: br.com.moip.resource.Order, authToken: String, eventName:String) {
        if(!isTokenValid(authToken, eventName)) throw HttpClientErrorException(HttpStatus.UNAUTHORIZED)
    }


}