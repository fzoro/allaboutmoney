package zoret4.allaboutmoney.order.model.service

import com.mongodb.BasicDBObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.model.domain.Customer
import zoret4.allaboutmoney.order.model.repository.CustomerRepository
import zoret4.allaboutmoney.order.model.service.contracts.CustomerService
import zoret4.allaboutmoney.order.model.service.contracts.PaymentProcessorService

@Service
class CustomerServiceImpl : CustomerService {

    companion object {
        val LOG = logger()
    }

    @Autowired
    lateinit var repo: CustomerRepository

    @Autowired
    @Qualifier("wirecardPaymentProcessorService")
    lateinit var paymentProcessor: PaymentProcessorService

    override fun postToVendor(customer: Customer): String {
        val vendorCustomer = paymentProcessor.postCustomer(customer)
        val dbVendorCustomer = BasicDBObject.parse(vendorCustomer)
        LOG.info("Customer posted to vendor. vendorCustomer={}", vendorCustomer)
        repo.save(customer.copy(vendor = dbVendorCustomer))
        val vendorCustomerId = dbVendorCustomer.getString("_id")
        return vendorCustomerId
    }
}