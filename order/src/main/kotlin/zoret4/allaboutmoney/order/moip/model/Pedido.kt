package zoret4.allaboutmoney.order.moip.model

import java.util.*

/*
1 . create Pedido (call MOIP)
1.1. returns Pedido
2. create Pagamento(call MOIP)
2.1 returns Pagamento
3. Activate webhooks or keep calling REST endpoints
 */
data class Pedido(val id:String,val ownId:String, val status: String, val createdAt: Date)
