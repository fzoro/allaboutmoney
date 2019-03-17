package zoret4.allaboutmoney.order.model.service.contracts

import com.mongodb.BasicDBObject
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.unwrap
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.domain.OrderStatus
import zoret4.allaboutmoney.order.model.exception.ValidationException
import zoret4.allaboutmoney.order.model.repository.CustomerRepository
import zoret4.allaboutmoney.order.model.repository.OrderRepository

@Service
class OrderServiceImpl : OrderService {

    companion object {
        val LOG = logger()
    }

    @Autowired
    lateinit var repo: OrderRepository

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var customerService: CustomerService

    @Autowired
    @Qualifier("wirecardPaymentProcessorService")
    lateinit var paymentProcessor: PaymentProcessorService



    override fun create(order: Order): String {
        val dbCustomer = customerRepository.findById(order.customerId).unwrap() ?: throw ResourceNotFoundException()
        //FIXME Whenever a new vendor is added, it might cause errors, since the customer might be posted to a vendor but not to other. :)
        val vendorCustomerId = if(null == dbCustomer.vendor)  customerService.postToVendor(dbCustomer) else dbCustomer.vendor.getString("id")
        val vendorOrder = order.payment.method.process(vendorCustomerId, order, paymentProcessor)
        val dbVendorOrder = Document.parse(vendorOrder)
        LOG.info("Order saved and posted to vendor. vendorOrder={}", vendorOrder)
        repo.save(order.copy(status = OrderStatus.PUBLISHED, vendor = dbVendorOrder))
        val payCheckoutUri = ((((dbVendorOrder.get("links") as Map<*,*>)
                ["checkout"] as Map<*,*>)
                ["payCheckout"] as Map<*,*>)
                ["redirectHref"] as String)

        LOG.debug("Order posted. payCheckoutUri={}", payCheckoutUri)
        return payCheckoutUri
    }
}

