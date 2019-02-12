package zoret4.allaboutmoney.order.web.resources

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import zoret4.allaboutmoney.order.model.domain.Customer
import zoret4.allaboutmoney.order.model.service.contracts.CustomerService

@RestController
@RequestMapping("/customers")
class CustomerResource {

    @Autowired
    lateinit var customerService: CustomerService

    @GetMapping("/{id}")
    fun get(@PathVariable id: String) = orNotFound(customerService.get(id))

    @PostMapping
    fun create(@RequestBody body: Customer) = ResponseEntity(customerService.create(body), HttpStatus.CREATED)

}