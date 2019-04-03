package zoret4.allaboutmoney.order.model.domain.factory

import zoret4.allaboutmoney.order.model.domain.*
import zoret4.allaboutmoney.order.model.strategy.PaymentStrategy


sealed class OrderTestFactory {

    companion object {
        fun simple() = Order(customerId = "test-001",
                products = setOf(Product(id = "product-1",
                        price = 10,
                        quantity = 2,
                        description = "montlhy"),
                        Product(id = "product-2",
                                price = 150,
                                quantity = 1,
                                description = "forever")
                ),
                payment = Payment(shipping = 10,
                        addition = 5,
                        discount = 4,
                        currency = Currency.BRL,
                        method = PaymentStrategy.VENDOR_CHECKOUT,
                        processor = PaymentProcessor.WIRECARD
                ), version = 0)


    }
}
