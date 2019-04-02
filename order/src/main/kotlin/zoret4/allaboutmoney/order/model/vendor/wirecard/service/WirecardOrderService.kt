package zoret4.allaboutmoney.order.model.vendor.wirecard.service

import br.com.moip.API
import br.com.moip.request.CustomerRequest
import br.com.moip.request.OrderAmountRequest
import br.com.moip.request.OrderRequest
import br.com.moip.request.SubtotalsRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.props.AppProperties
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
        val wirecardOrder = api.order().get(orderId)
        LOG.info("success on getting wirecard order by id={}", orderId)
        LOG.debug("wirecard.order={}", wirecardOrder)
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wirecardOrder)
    }

    override fun checkoutByVendor(vendorCustomerId: String, order: Order): String {
        val orderRequest: OrderRequest
        with(order) {
            orderRequest = OrderRequest()
                    .ownId(id)
                    .customer(CustomerRequest().id(vendorCustomerId))
                    .amount(OrderAmountRequest().currency(payment.currency.toString())
                            .subtotals(SubtotalsRequest()
                                    .shipping(payment.shipping)
                                    .addition(payment.addition)
                                    .discount(payment.discount)
                            )
                    )
            products.forEach { orderRequest.addItem(it.id, it.quantity, it.description, it.price) }
        }
        LOG.info("Posting OrderRequest to WireCard: query={}", orderRequest)
        val wirecardOrder = api.order().create(orderRequest)
        LOG.debug("wirecard order created on their side. response={}", wirecardOrder)
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wirecardOrder)
    }

}