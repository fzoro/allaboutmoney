package zoret4.allaboutmoney.order.model.domain

import com.mongodb.BasicDBObject
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import zoret4.allaboutmoney.order.model.strategy.PaymentStrategy
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

@Document
data class Order(
        @Id val id: String = UUID.randomUUID().toString(),
        val vendor: BasicDBObject?,
        val customerId: Serializable,
        val status: OrderStatus = OrderStatus.DRAFT,
        val products: Set<Product>,
        val payment: Payment)

data class Product(val id: Serializable, val price: Int, val quantity:Int, val description: String)

// wirecard only accepts int.
// Valor de frete do item, será somado ao valor dos itens. Em centavos. Ex: R$10,32 deve ser informado 1032. Limite de caracteres: 9.
data class Payment(
        val shipping: Int,
        val addition: Int,
        val discount: Int,
        val currency: Currency,
        val method: PaymentStrategy,
        val processor: PaymentProcessor
)

enum class OrderStatus {
    DRAFT, //(created on mongo db) - TO CREATE AN ORDER INDEPENDENT OF MAKING THE PAYMENT (USEFUL FOR CC PAYMENTS)
    PUBLISHED, // (posted to the payment processor) ( USEFUL FOR BOLETO and CC PAYMENTS )
    ERROR_FROM_PROCESSOR, // error from processor
    SUCCESS_FROM_PROCESSOR // success_from_processor
}

enum class PaymentProcessor {
    WIRECARD
}

enum class Currency {
    BRL, US
}