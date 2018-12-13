package zoret4.allaboutmoney.order.model.service.contracts

import zoret4.allaboutmoney.order.model.domain.Order
import java.util.*

interface OrderService {
    fun placeOrder(order: Order): Order
    fun get(id: UUID): Order?
}