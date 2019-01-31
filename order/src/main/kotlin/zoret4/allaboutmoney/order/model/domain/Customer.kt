package zoret4.allaboutmoney.order.model.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class Customer(
        @Id val id: String,
        @Indexed val vendorId: String,
        val fullName: String,
        val email: String,
        val birthDate: LocalDate,
        val taxonomyId: String,
        val taxonomyType: TaxonomyType,
        val phoneNumber: String,
        val address: Address,
        val tracer: Tracer
) {
    fun isFull(): Boolean = email != null && email.isNotEmpty()
}

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