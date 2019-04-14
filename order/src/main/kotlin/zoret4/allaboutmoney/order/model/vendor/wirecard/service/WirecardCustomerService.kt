package zoret4.allaboutmoney.order.model.vendor.wirecard.service

import br.com.moip.API
import br.com.moip.request.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.configuration.toDate
import zoret4.allaboutmoney.order.model.domain.Customer
import zoret4.allaboutmoney.order.model.domain.TaxonomyType
import zoret4.allaboutmoney.order.model.service.contracts.vendor.VendorCustomerService

@Service
class WirecardCustomerService(private val props: AppProperties,
                              private val objectMapper: ObjectMapper,
                              private val api: API) : VendorCustomerService {
    companion object {
        val LOG = logger()
    }


    override fun postCustomer(customer: Customer): String {
        val customerRequest: CustomerRequest
        with(customer) {
            customerRequest = CustomerRequest()
                    .ownId(id)
                    .fullname(fullName)
                    .email(email)
                    .birthdate(ApiDateRequest().date(birthDate.toDate()))
                    .shippingAddressRequest(ShippingAddressRequest()
                            .country(address.country)
                            .state(address.state)
                            .city(address.city)
                            .streetNumber(address.streetNumber)
                            .street(address.street)
                            .zipCode(address.zipCode)
                            .complement(address.complement)
                            .district(address.district)

                    ).phone(PhoneRequest()
                            .countryCode(phone.countryCode)
                            .setAreaCode(phone.areaCode)
                            .setNumber(phone.number)
                    ).taxDocument(
                            if (taxonomyType == TaxonomyType.CPF)
                                TaxDocumentRequest.cpf(taxonomyId)
                            else
                                TaxDocumentRequest.cnpj(taxonomyId)
                    )
        }
        val wirecardCustomer = api.customer().create(customerRequest)
        LOG.debug("customer posted to wirecard service. response={}.", wirecardCustomer)
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wirecardCustomer)
    }
}