package zoret4.allaboutmoney.order.model.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Document
data class Order(
        @Id val id: UUID = UUID.randomUUID(), val customerId: String, val status: OrderStatus = OrderStatus.CREATED,
        val products: Set<Product>,
        val amount: Amount,
        val payment: Payment)

data class Product(val id: String, val price: BigDecimal, val links: Set<String>)
data class Amount(val total: BigDecimal, val currency: String)

data class Payment(val publishedAt: LocalDateTime?, val method: PaymentMethod, val processor: String?)

enum class PaymentMethod {
    BAR_CODE,
    CREDIT_CARD
}

enum class OrderStatus {
    CREATED, //(created on mongo db) - TO CREATE AN ORDER INDEPENDENT OF MAKING THE PAYMENT (USEFUL FOR CC PAYMENTS)
    QUEUED, //( queued to redis) ( USEFUL FOR CC PAYMENTS)
    PUBLISHED, // (posted to the payment processor) ( USEFUL FOR BOLETO and CC PAYMENTS )
    ERROR_FROM_PROCESSOR, // error from processor
    SUCCESS_FROM_PROCESSOR // success_from_processor
}