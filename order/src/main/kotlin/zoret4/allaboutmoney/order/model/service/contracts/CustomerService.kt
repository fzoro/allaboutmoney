package zoret4.allaboutmoney.order.model.service.contracts

import zoret4.allaboutmoney.order.model.domain.Customer

interface CustomerService {
    fun create(customer: Customer): Customer
    fun get(id: String): Customer?
}