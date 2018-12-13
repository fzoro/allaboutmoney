package zoret4.allaboutmoney.order.model.service.contracts

import zoret4.allaboutmoney.order.model.domain.Order

interface PaymentService {
    fun createPayment(order: Order): Order
}