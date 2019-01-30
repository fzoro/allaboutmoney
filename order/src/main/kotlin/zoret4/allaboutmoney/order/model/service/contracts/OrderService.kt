package zoret4.allaboutmoney.order.model.service.contracts

import zoret4.allaboutmoney.order.model.domain.Order
import java.util.*

interface OrderService {
    fun create(order: Order): String
    fun get(id: UUID): Order?
}