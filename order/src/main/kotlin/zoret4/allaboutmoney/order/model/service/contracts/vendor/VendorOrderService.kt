package zoret4.allaboutmoney.order.model.service.contracts.vendor

import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.domain.OrderStatus

interface VendorOrderService {

    /**
     * @return order json
     */
    fun checkoutByVendor(vendorCustomerId: String, order: Order): String

    /**
     * @return Order json
     */
    fun getOrder(orderId: String): String

    fun handlePaymentEvent(body: Map<*, *>) : OrderStatus

    fun handleOrderEvent(body: Map<*, *>) : OrderStatus
}