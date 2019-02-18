package zoret4.allaboutmoney.order.model.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.rest.core.annotation.RestResource
import zoret4.allaboutmoney.order.model.domain.Customer

@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
interface CustomerRepository : PagingAndSortingRepository<Customer, String> {

    @RestResource(exported = false)
    override fun delete(entity: Customer)

    @RestResource(exported = false)
    override fun deleteById(id: String)

}
