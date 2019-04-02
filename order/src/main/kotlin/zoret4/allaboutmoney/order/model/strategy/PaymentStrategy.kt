package zoret4.allaboutmoney.order.model.strategy

import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.service.contracts.vendor.VendorOrderService

enum class PaymentStrategy {
    VENDOR_CHECKOUT {
        override fun process(vendorCustomerId: String, order: Order, vendorService: VendorOrderService): String {
            return vendorService.checkoutByVendor(vendorCustomerId, order)
        }
    };

    abstract fun process(vendorCustomerId: String, order: Order, vendorService: VendorOrderService): String
}
