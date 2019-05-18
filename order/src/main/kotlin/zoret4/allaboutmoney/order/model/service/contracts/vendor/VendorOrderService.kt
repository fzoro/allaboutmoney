package zoret4.allaboutmoney.order.model.service.contracts.vendor

import br.com.moip.resource.Payment
import zoret4.allaboutmoney.order.model.domain.Order

interface VendorOrderService {

    /**
     * @return order json
     */
    fun checkoutByVendor(vendorCustomerId: String, order: Order): String

    /**
     * @return Order json
     */
    fun getOrder(orderId: String): String

    fun handlePaymentEvent(payment: Payment, authToken: String, eventName:String)

    fun handleOrderEvent( order : br.com.moip.resource.Order, authToken: String, eventName:String)
}