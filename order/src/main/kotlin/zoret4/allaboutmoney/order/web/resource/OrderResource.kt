package zoret4.allaboutmoney.order.web.resources

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.service.contracts.OrderService

@RestController
@RequestMapping("/orders")
class OrderResource {

    @Autowired
    lateinit var orderService: OrderService


    @PostMapping
    fun create(@RequestBody body: Order) = ResponseEntity(orderService.create(body), HttpStatus.CREATED)

    @GetMapping("/{id}")
    fun get(@PathVariable id:String) = ResponseEntity(orderService.get(id), HttpStatus.OK)

}