package zoret4.allaboutmoney.order.model.service.contracts

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.configuration.unwrap
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.domain.OrderStatus
import zoret4.allaboutmoney.order.model.repository.OrderMongoRepository
import java.util.*

@Service
class OrderServiceImpl(private val props: AppProperties) : OrderService {


    companion object {
        val LOG = logger()
    }
    @Autowired
    lateinit var repo: OrderMongoRepository

    @Autowired
    @Qualifier("wirecardPaymentProcessorService")
    lateinit var paymentProcessor: PaymentProcessorService

    override fun get(id: UUID): Order? = repo.findById(id).unwrap()

    override fun create(order: Order): String {
        repo.save(order)
        LOG.debug("Order saved here. Requesting order creation on Vendor pre defined=wirecardPaymentProcessorService")
        val payCheckoutUri = order.payment.method.process(order, paymentProcessor)
        LOG.info("Order saved and posted to vendor.(Order.id={}, Vendor.checkouturi={}", order.id, payCheckoutUri)
        repo.save(order.copy(status = OrderStatus.PUBLISHED))
        return payCheckoutUri

    }
}