package zoret4.allaboutmoney.order.web.vendor.resource

import br.com.moip.resource.Order
import br.com.moip.resource.Payment
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import zoret4.allaboutmoney.order.model.vendor.wirecard.service.WirecardOrderService


@RestController
@RequestMapping("/wirecard/events")
class WirecardWebhookEventResource(
        private val wirecardOrderService: WirecardOrderService) {


    companion object {
        val ORDER_EVENT_NAME = "ORDER"
        val PAYMENT_EVENT_NAME = "PAYMENT"
    }

    @PostMapping("/payment")
    @ResponseStatus(HttpStatus.OK)
    fun handlePaymentEvent(@RequestBody body: Payment,
                           @RequestHeader("Authorization") authToken: String) = wirecardOrderService.handlePaymentEvent(body, authToken, ORDER_EVENT_NAME)

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    fun handleOrderEvent(@RequestBody body: Order,
                         @RequestHeader("Authorization") authToken: String) = wirecardOrderService.handleOrderEvent(body, authToken, PAYMENT_EVENT_NAME)

}