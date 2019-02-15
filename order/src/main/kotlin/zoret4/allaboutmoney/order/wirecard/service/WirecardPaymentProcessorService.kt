package zoret4.allaboutmoney.order.wirecard.service

//import java.net.http.HttpClient
//import java.net.http.HttpRequest
//import java.net.http.HttpResponse
import br.com.moip.API
import br.com.moip.Client
import br.com.moip.authentication.BasicAuth
import br.com.moip.request.CustomerRequest
import br.com.moip.request.OrderAmountRequest
import br.com.moip.request.OrderRequest
import br.com.moip.request.SubtotalsRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.service.contracts.PaymentProcessorService
import br.com.moip.resource.Customer as WirecardCustomer
import br.com.moip.resource.Order as WirecardOrder


@Service
class WirecardPaymentProcessorService(
        private val props: AppProperties,
        private val objectMapper: ObjectMapper) : PaymentProcessorService {

    private val auth: BasicAuth = BasicAuth(props.upstream.wirecard.token, props.upstream.wirecard.key)
    private val client: Client
    private val api: API

    companion object {
        val LOG = logger()
    }

    init {
        client = Client(Client.SANDBOX, auth)
        api = API(client)
    }


    override fun checkoutByVendor(vendorCustomerId: String, order: Order): String {
        val orderRequest: OrderRequest
        with(order) {
            orderRequest = OrderRequest()
                    .ownId(id)
                    .customer(CustomerRequest().id(vendorCustomerId))
                    .amount(OrderAmountRequest()
                            .currency(payment.currency.toString())
                            .subtotals(SubtotalsRequest()
                                    .shipping(payment.shipping)
                                    .addition(payment.addition)
                                    .discount(payment.discount)
                            )
                    )
            products.forEach { orderRequest.addItem(it.id.toString(), it.quantity, it.description, it.price) }
        }
        LOG.info("Posting OrderRequest to WireCard: query={}", orderRequest)
        val wirecardOrder = api.order().create(orderRequest)
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wirecardOrder)
    }

//    fun getOrCreateCustomer(customer: Customer): WirecardCustomer {
//
//        var vendorCustomer: WirecardCustomer? = null
//
//        try {
//            vendorCustomer = getCustomer(customer.vendorId)
//        } catch (e: ValidationException) {
//            if (HttpStatus.NOT_FOUND.value() != e.responseCode) {
//                throw e
//            }
//        }
//        TODO()
////        return vendorCustomer ?: createCustomer()
//    }
//
//    fun getCustomer(vendorCustomerId: Serializable?): WirecardCustomer = api.customer().get(vendorCustomerId?.toString())
//
//    fun createCustomer(customer: Customer): WirecardCustomer {
//        return with(customer) {
//            api.customer().create(CustomerRequest()
//                    .ownId(id.toString())
//                    .fullname(fullName)
//                    .email(email)
//                    .birthdate(ApiDateRequest().date(birthDate.toDate()))
//                    .taxDocument(TaxDocumentRequest.cpf(taxonomyId))
//                    .phone(PhoneRequest().setAreaCode(phoneNumber).setNumber(customer.phoneNumber))
//                    .shippingAddressRequest(ShippingAddressRequest()
//                            .street(customer.address.street)
//                            .streetNumber(customer.address.streetNumber)
//                            .complement(customer.address.complement)
//                            .city(customer.address.city)
//                            .state(customer.address.state)
//                            .district(customer.address.district)
//                            .country(customer.address.country)
//                            .zipCode(customer.address.zipCode)))
//        }
//    }
}