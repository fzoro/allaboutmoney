package zoret4.allaboutmoney.order.model.service.contracts

import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.enums.VendorWebhookEvent

interface OrderService {
    fun create(order: Order): String
    fun get(id: String): Order
    fun handleEvent(eventBody: Map<*, *>, authToken: String, event: VendorWebhookEvent)
}