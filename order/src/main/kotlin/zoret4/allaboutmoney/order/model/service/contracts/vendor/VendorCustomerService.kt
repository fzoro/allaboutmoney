package zoret4.allaboutmoney.order.model.service.contracts.vendor

import zoret4.allaboutmoney.order.model.domain.Customer

interface VendorCustomerService {

    /**
     * @return customer json
     */
    fun postCustomer(customer: Customer): String

}