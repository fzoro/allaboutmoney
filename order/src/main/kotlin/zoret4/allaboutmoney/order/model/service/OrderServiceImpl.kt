package zoret4.allaboutmoney.order.model.service.contracts

import com.mongodb.BasicDBObject
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
    @Qualifier("wirecardPaymentProcessorService")
    lateinit var paymentProcessor: PaymentProcessorService


    override fun create(order: Order): String {
        val dbCustomer = customerRepository.findById(order.customerId).unwrap() ?: throw ResourceNotFoundException()
        if (null == dbCustomer.vendor) throw ValidationException("User ${order.customerId} not posted to Wirecard yet")
        val vendorCustomerId = dbCustomer.vendor.getString("id")
        val vendorOrder = order.payment.method.process(vendorCustomerId, order, paymentProcessor)
        val dbVendorOrder = BasicDBObject.parse(vendorOrder)
        LOG.info("Order saved and posted to vendor. vendorOrder={}", vendorOrder)
        repo.save(order.copy(status = OrderStatus.PUBLISHED, vendor = dbVendorOrder))
        val payCheckoutUri = dbVendorOrder.getString("_links.checkout.payCheckout.redirectHref")
        LOG.debug("Order posted. payCheckoutUri={}", payCheckoutUri)
        return payCheckoutUri
    }
}

