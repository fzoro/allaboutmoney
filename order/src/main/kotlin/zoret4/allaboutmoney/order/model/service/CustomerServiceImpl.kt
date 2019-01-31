package zoret4.allaboutmoney.order.model.service.contracts

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.configuration.unwrap
import zoret4.allaboutmoney.order.model.domain.Customer
import zoret4.allaboutmoney.order.model.repository.CustomerMongoRepository

@Service
class CustomerServiceImpl(private val props: AppProperties) : CustomerService {

    companion object {
        val LOG = logger()
    }

    @Autowired
    lateinit var repo: CustomerMongoRepository

    override fun get(id: String): Customer? = repo.findById(id).unwrap()

    override fun create(customer: Customer): Customer {
        repo.save(customer)
        LOG.info("Customer saved. Customer.id = {}.", customer.id)
        return customer
    }
}