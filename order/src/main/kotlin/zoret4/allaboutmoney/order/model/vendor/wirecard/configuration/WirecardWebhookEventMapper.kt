package zoret4.allaboutmoney.order.model.vendor.wirecard.configuration

import zoret4.allaboutmoney.order.model.domain.OrderStatus
import br.com.moip.resource.OrderStatus as WirecardOrderStatus

object WirecardWebhookEventMapper {

    val orderEvents = mapOf(
            WirecardOrderStatus.CREATED to OrderStatus.CREATED,
            WirecardOrderStatus.WAITING to OrderStatus.WAITING_VENDOR,
            WirecardOrderStatus.PAID to OrderStatus.PAID,
            WirecardOrderStatus.NOT_PAID to OrderStatus.NOT_PAID,
            WirecardOrderStatus.REVERTED to OrderStatus.REVERTED
    )
}