package zoret4.allaboutmoney.order.model.service.contracts

import zoret4.allaboutmoney.order.model.domain.Order

interface PaymentProcessorService {

    fun processPaymentBarCode(order:Order) :Pair<Order,Order>
    fun processPaymentCreditCard(order:Order) :Pair<Order,Order>
}