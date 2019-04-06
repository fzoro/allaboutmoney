package zoret4.allaboutmoney.order.model.domain.factory

import zoret4.allaboutmoney.order.model.domain.Address
import zoret4.allaboutmoney.order.model.domain.Customer
import zoret4.allaboutmoney.order.model.domain.Phone
import zoret4.allaboutmoney.order.model.domain.TaxonomyType
import java.time.LocalDate
import java.util.*


sealed class CustomerTestFactory {

    companion object {
        fun simple() = Customer(
                id = UUID.randomUUID().toString(),
                vendor = null,
                fullName = "String",
                email = "String",
                birthDate = LocalDate.now(),
                taxonomyId = "String",
                taxonomyType = TaxonomyType.CPF,
                phoneNumber = Phone("+1", "000", "1234"),
                address = Address(street = "String",
                        streetNumber = "String",
                        complement = "String",
                        city = "String",
                        state = "String",
                        district = "String",
                        country = "String",
                        zipCode = "String"),
                version = 0L
        )
    }
}
