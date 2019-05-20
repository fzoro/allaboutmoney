package zoret4.allaboutmoney.order.web.resource

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import zoret4.allaboutmoney.order.model.enums.VendorWebhookEvent
import zoret4.allaboutmoney.order.model.service.contracts.OrderService


@RestController
@RequestMapping("/wirecard/events")
@ResponseStatus(HttpStatus.OK)
class WebhookEventResource(
        private val orderService: OrderService) {


    @PostMapping("/payment")
    fun handlePaymentEvent(@RequestBody body: Map<*, *>, @RequestHeader("Authorization") authToken: String) = orderService.handleEvent(body, authToken, VendorWebhookEvent.PAYMENT)

    @PostMapping("/order")
    fun handleOrderEvent(@RequestBody body: Map<*, *>, @RequestHeader("Authorization") authToken: String) = orderService.handleEvent(body, authToken, VendorWebhookEvent.ORDER)

}