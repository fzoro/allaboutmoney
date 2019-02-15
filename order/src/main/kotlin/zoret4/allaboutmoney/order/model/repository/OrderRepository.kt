package zoret4.allaboutmoney.order.model.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.rest.core.annotation.RestResource
import zoret4.allaboutmoney.order.model.domain.Order


@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
interface OrderRepository : PagingAndSortingRepository<Order, String> {

    @RestResource(exported = false)
    override fun deleteById(id: String)

    @RestResource(exported = false)
    override fun delete(entity: Order)

    @RestResource(exported = false)
    override fun <S : Order?> save(entity: S): S

}