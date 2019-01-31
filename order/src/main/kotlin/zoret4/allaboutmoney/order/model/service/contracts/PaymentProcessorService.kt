package zoret4.allaboutmoney.order.model.service.contracts

import zoret4.allaboutmoney.order.model.domain.Order

interface PaymentProcessorService {

    fun checkoutByVendor(order: Order): String
}