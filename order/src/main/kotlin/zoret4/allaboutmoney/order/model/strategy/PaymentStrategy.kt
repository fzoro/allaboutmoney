package zoret4.allaboutmoney.order.model.strategy

import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.service.contracts.PaymentProcessorService

enum class PaymentStrategy {
    VENDOR_CHECKOUT {
        override fun process(order: Order, processor: PaymentProcessorService): String {
            return processor.checkoutByVendor(order)
        }
    };

    abstract fun process(order: Order, processor: PaymentProcessorService): String
}
