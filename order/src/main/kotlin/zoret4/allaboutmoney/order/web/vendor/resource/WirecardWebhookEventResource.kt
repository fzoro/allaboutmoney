package zoret4.allaboutmoney.order.web.vendor.resource

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.vendor.wirecard.service.WirecardOrderService


@RestController
@RequestMapping("/wirecard/events")
class WirecardWebhookEventResource(
        private val wirecardOrderService: WirecardOrderService) {


    @PostMapping("/payment")
    @ResponseStatus(HttpStatus.OK)
    fun capturePaymentEvent(@RequestBody body: Order) {

    }

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    fun captureOrderEvent(@RequestBody body: Order,
                          @RequestHeader("Authorization") authToken: String) {

    }
}