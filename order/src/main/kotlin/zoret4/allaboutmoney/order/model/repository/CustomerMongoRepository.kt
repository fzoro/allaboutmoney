package zoret4.allaboutmoney.order.model.repository

import org.springframework.data.repository.CrudRepository
import zoret4.allaboutmoney.order.model.domain.Customer
import java.util.*

interface CustomerMongoRepository : CrudRepository<Customer, String>
