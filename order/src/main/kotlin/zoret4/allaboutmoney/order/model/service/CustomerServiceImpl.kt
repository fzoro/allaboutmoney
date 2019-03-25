package zoret4.allaboutmoney.order.model.service

import org.bson.Document
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.model.domain.Customer
import zoret4.allaboutmoney.order.model.repository.CustomerRepository
import zoret4.allaboutmoney.order.model.service.contracts.CustomerService
import zoret4.allaboutmoney.order.model.service.contracts.PaymentProcessorService

@Service
class CustomerServiceImpl(private val repo: CustomerRepository,
                          @Qualifier("wirecardPaymentProcessorService")
                          private val paymentProcessor: PaymentProcessorService) : CustomerService {

    companion object {
        val LOG = logger()
    }

    override fun postToVendor(customer: Customer): String {
        val vendorCustomer = paymentProcessor.postCustomer(customer)
        val dbVendorCustomer = Document.parse(vendorCustomer)
        LOG.info("Customer posted to vendor. vendorCustomer={}", vendorCustomer)
        val updatedCustomer = customer.copy(vendor = dbVendorCustomer)
        repo.save(updatedCustomer)
        return dbVendorCustomer.getString("id")
    }
}