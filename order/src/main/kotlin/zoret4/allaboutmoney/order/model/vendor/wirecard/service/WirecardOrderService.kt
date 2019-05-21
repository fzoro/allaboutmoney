package zoret4.allaboutmoney.order.model.vendor.wirecard.service

import br.com.moip.API
import br.com.moip.request.CustomerRequest
import br.com.moip.request.OrderAmountRequest
import br.com.moip.request.OrderRequest
import br.com.moip.request.SubtotalsRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.toDigit
import zoret4.allaboutmoney.order.configuration.toJsonWithMapper
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.domain.OrderStatus
import zoret4.allaboutmoney.order.model.exception.OrderConfigurationException
import zoret4.allaboutmoney.order.model.exception.VendorIntegrationException
import zoret4.allaboutmoney.order.model.service.contracts.vendor.VendorOrderService
import zoret4.allaboutmoney.order.model.vendor.wirecard.configuration.WirecardWebhookEventMapper

@Service
class WirecardOrderService(private val objectMapper: ObjectMapper,
                           private val api: API) : VendorOrderService {
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

    /**
     *  Values allowed https://dev.wirecard.com.br/reference#recebendo-webhooks-mp
     */
    override fun handleOrderEvent(body: Map<*, *>): OrderStatus {

        body["event"]
                ?.let { br.com.moip.resource.OrderStatus.valueOf((it as String).split(".")[1]) }
                ?.run {
                    return WirecardWebhookEventMapper.orderEvents[this]
                            ?: throw OrderConfigurationException("Couldn't find the mapping of $this with current configuration")
                }
        throw VendorIntegrationException("'event' field was not present on payload sent by WIRECARD")
    }

    override fun handlePaymentEvent(body: Map<*, *>): OrderStatus {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}