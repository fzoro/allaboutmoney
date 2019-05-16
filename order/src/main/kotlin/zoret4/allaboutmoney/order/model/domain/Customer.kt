package zoret4.allaboutmoney.order.model.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime

@Document
data class Customer(
        @Id val id: String,
        val vendor: Map<*, *>?,
        val fullName: String,
        val email: String,
        val birthDate: LocalDate,
        val taxonomyId: String,
        val taxonomyType: TaxonomyType,
        val phone: Phone,
        val address: Address, // refactor to a collection.
        @Version var version: Long?
)

data class Phone(val countryCode: String, val areaCode: String, val number: String)

data class Address(val street: String,
                   val streetNumber: String,
                   val complement: String?,
                   val city: String,
                   val state: String,
                   val district: String?,
                   val country: String,
                   val zipCode: String)

enum class TaxonomyType {
    CPF, CNPJ, ITIN, SSN
}



