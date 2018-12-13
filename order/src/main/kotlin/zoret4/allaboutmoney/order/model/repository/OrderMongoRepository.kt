package zoret4.allaboutmoney.order.model.repository

import org.springframework.data.repository.CrudRepository
import zoret4.allaboutmoney.order.model.domain.Order
import java.util.*

interface OrderMongoRepository : CrudRepository<Order, UUID>