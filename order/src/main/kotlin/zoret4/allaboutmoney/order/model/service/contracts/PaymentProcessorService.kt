package zoret4.allaboutmoney.order.model.service.contracts

import zoret4.allaboutmoney.order.model.domain.Customer
import zoret4.allaboutmoney.order.model.domain.Order

interface PaymentProcessorService {

    /**
     * @return order json
     */
    fun checkoutByVendor(vendorCustomerId: String, order: Order): String

    /**
     * @return customer json
     */
    fun postCustomer(customer: Customer): String

    /**
     * @return Order json
     */
    fun getOrder(orderId: String) : String
}