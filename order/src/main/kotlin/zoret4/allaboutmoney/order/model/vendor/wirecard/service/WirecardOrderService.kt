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
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.configuration.toJsonWithMapper
import zoret4.allaboutmoney.order.configuration.toWirecardMoney
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.service.contracts.vendor.VendorOrderService

@Service
class WirecardOrderService(private val props: AppProperties,
                           private val objectMapper: ObjectMapper,
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
        val orderRequest: OrderRequest
        with(order) {
            orderRequest = OrderRequest()
                    .ownId(id)
                    .customer(CustomerRequest().id(vendorCustomerId))
                    .amount(OrderAmountRequest().currency(payment.currency.name)
                            .subtotals(SubtotalsRequest()
                                    .shipping(payment.shipping.toWirecardMoney())
                                    .addition(payment.addition.toWirecardMoney())
                                    .discount(payment.discount.toWirecardMoney())
                            )
                    )
            products.forEach { orderRequest.addItem(it.id, it.quantity, it.description, it.price.toWirecardMoney()) }
        }
        LOG.info("Posting OrderRequest to WireCard: query={}", orderRequest)
        val wOrder = api.order().create(orderRequest)
        LOG.debug("wirecard order created on their side. response={}", wOrder)
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wOrder)
    }

}