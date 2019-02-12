package zoret4.allaboutmoney.order.model.domain.factory

import zoret4.allaboutmoney.order.model.domain.Address
import zoret4.allaboutmoney.order.model.domain.Customer
import zoret4.allaboutmoney.order.model.domain.TaxonomyType
import zoret4.allaboutmoney.order.model.domain.Tracer
import java.time.LocalDate
import java.util.*


sealed class CustomerTestFactory {

    companion object {
        fun simple(): Customer = Customer(
                id = UUID.randomUUID().toString(),
                vendorId = "String",
                fullName = "String",
                email = "String",
                birthDate = LocalDate.now(),
                taxonomyId = "String",
                taxonomyType = TaxonomyType.CPF,
                phoneNumber = "String",
                address = Address(street = "String",
                        streetNumber = "String",
                        complement = "String",
                        city = "String",
                        state = "String",
                        district = "String",
                        country = "String",
                        zipCode = "String"),
                tracer = Tracer(
                        requestId = "String",
                        createdBy = "String",
                        origin = "String")

        )


    }
}
