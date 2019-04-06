package zoret4.allaboutmoney.order.model.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document
import zoret4.allaboutmoney.order.model.strategy.PaymentStrategy
import java.util.*

@Document
data class Order(
        @Id val id: String = UUID.randomUUID().toString(),
        val vendor: Map<*, *>? = null,
        val customerId: String,
        val status: OrderStatus = OrderStatus.DRAFT,
        val products: Set<Product>,
        val payment: Payment,
        val events: Map<*, *>? = null,
        @Version var version: Long?)

data class Product(val id: String, val price: String, val quantity: Int, val description: String)

data class Payment(
        val shipping: String,
        val addition: String,
        val discount: String,
        val currency: Currency,
        val method: PaymentStrategy,
        val processor: PaymentProcessor
)

enum class OrderStatus {
    DRAFT,// only created on app's database
    CREATED, // posted to vendor successfully
    WAITING_VENDOR, // waiting confirmation from vendor
    PAID, // paid on vendor
    NOT_PAID, //canceled by vendor
    REVERTED // reverted/chargeback by vendor
}

enum class PaymentProcessor {
    WIRECARD
}

enum class Currency {
    BRL, US
}