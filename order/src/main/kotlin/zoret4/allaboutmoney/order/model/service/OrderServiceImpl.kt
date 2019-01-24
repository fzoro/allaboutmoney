package zoret4.allaboutmoney.order.model.service.contracts

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.unwrap
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.domain.OrderStatus
import zoret4.allaboutmoney.order.model.domain.PaymentMethod
import zoret4.allaboutmoney.order.model.repository.OrderMongoRepository
import java.util.*

@Service
class OrderServiceImpl : OrderService {

    @Autowired
    lateinit var repo: OrderMongoRepository

    @Autowired
    lateinit var paymentProcessor: PaymentProcessorService

    override fun get(id: UUID): Order? = repo.findById(id).unwrap()

    //1. create order .  (CREATED)
    //2. create paymentProcessor purchase(may include many calls)
    //3. store everything important from paymentProcessor response
    //4. return needed information to the user

    override fun placeOrder(order: Order): Order {

        repo.save(order)
        val (processedOrder, orderToView) = processPayment(order)
        repo.save(processedOrder.copy(status = OrderStatus.PUBLISHED))

        return orderToView
    }

    fun processPayment(order: Order) = when (order.payment.method) {
        PaymentMethod.BAR_CODE -> paymentProcessor.processPaymentBarCode(order)
        PaymentMethod.CREDIT_CARD -> paymentProcessor.processPaymentCreditCard(order)
    }

}