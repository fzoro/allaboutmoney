package zoret4.allaboutmoney.order.model.service.contracts

import zoret4.allaboutmoney.order.model.domain.Customer
import zoret4.allaboutmoney.order.model.domain.Order

interface PaymentProcessorService {

    fun checkoutByVendor(vendorCustomerId:String, order: Order): String
    fun postCustomer(customer: Customer): String
}