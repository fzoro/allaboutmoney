package zoret4.allaboutmoney.order.wirecard.service

//import java.net.http.HttpClient
//import java.net.http.HttpRequest
//import java.net.http.HttpResponse
import br.com.moip.API
import br.com.moip.Client
import br.com.moip.authentication.BasicAuth
import br.com.moip.exception.ValidationException
import br.com.moip.request.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.configuration.toDate
import zoret4.allaboutmoney.order.model.domain.Customer
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.service.contracts.PaymentProcessorService
import br.com.moip.resource.Customer as WirecardCustomer


@Service
class WirecardPaymentProcessorService(val props: AppProperties) : PaymentProcessorService {

    companion object {
        val LOG = logger()
    }

    private val auth = BasicAuth("TOKEN", "SECRET")
    private val client = Client(Client.SANDBOX, auth)
    private val api = API(client)

    override fun checkoutByVendor(order: Order): String {
        TODO()
    }

    fun getOrCreateCustomer(customer: Customer): WirecardCustomer {

        var vendorCustomer: WirecardCustomer? = null

        try {
            vendorCustomer = getCustomer(customer.vendorId)
        } catch (e: ValidationException) {
            if (HttpStatus.NOT_FOUND.value() != e.responseCode) {
                throw e
            }
        }
        TODO()
//        return vendorCustomer ?: createCustomer()
    }

    fun getCustomer(vendorCustomerId: String): WirecardCustomer = api.customer().get(vendorCustomerId)

    fun createOrder(): br.com.moip.resource.Order {

        return api.order().create(OrderRequest()
                .ownId("order-id-thisproject")
                .amount(OrderAmountRequest()
                        .currency("BRL")
                        .subtotals(SubtotalsRequest()
                                .shipping(1000)
                                .addition(100)
                                .discount(500)
                        )
                )
                .addItem("Nome do produto 1", 1, "Mais info...", 100)
                .addItem("Nome do produto 2", 2, "Mais info...", 200)
                .addItem("Nome do produto 3", 3, "Mais info...", 300)
                .customer(CustomerRequest()
                        .id("CUSTOMER_ID")
                )
                .addReceiver(ReceiverRequest()
                        .secondary("MOIP_ACCOUNT_ID", AmountRequest().percentual(50), false)
                )
        )

    }

    fun createCustomer(customer: Customer): WirecardCustomer {
        return with(customer) {
            api.customer().create(CustomerRequest()
                    .ownId(id.toString())
                    .fullname(fullName)
                    .email(email)
                    .birthdate(ApiDateRequest().date(birthDate.toDate()))
                    .taxDocument(TaxDocumentRequest.cpf(taxonomyId))
                    .phone(PhoneRequest().setAreaCode(phoneNumber).setNumber(customer.phoneNumber))
                    .shippingAddressRequest(ShippingAddressRequest()
                            .street(customer.address.street)
                            .streetNumber(customer.address.streetNumber)
                            .complement(customer.address.complement)
                            .city(customer.address.city)
                            .state(customer.address.state)
                            .district(customer.address.district)
                            .country(customer.address.country)
                            .zipCode(customer.address.zipCode)))
        }
    }
}