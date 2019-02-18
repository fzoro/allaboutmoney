package zoret4.allaboutmoney.order.model.service.contracts

import zoret4.allaboutmoney.order.model.domain.Customer

interface CustomerService {

    fun postToVendor(customer: Customer): String
}