package zoret4.allaboutmoney.order.moip.service

import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.service.contracts.PaymentProcessorService
import java.net.URI
//import java.net.http.HttpClient
//import java.net.http.HttpRequest
//import java.net.http.HttpResponse
import java.time.LocalDateTime
import org.bouncycastle.asn1.x500.style.RFC4519Style.street
import org.bouncycastle.crypto.tls.ConnectionEnd.client
import org.apache.tomcat.util.net.openssl.ciphers.Authentication



@Service
class WirecardPaymentProcessorService (private val props:AppProperties): PaymentProcessorService {

    companion object {
        const val PROCESSOR_NAME: String = "Wirecard"
    }

    override fun processPaymentCreditCard(order: Order): Pair<Order, Order> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun processPaymentBarCode(order: Order): Pair<Order, Order> {
        TODO("not implemented")
    }
}
//        val auth = BasicAuth("TOKEN", "SECRET")
//
//        val client = Client(Client.SANDBOX, auth)
//
//        val api = API(client)
//
//        val customer = api.customer().create(CustomerRequest()
//                .ownId("CUS-" + System.currentTimeMillis())
//                .fullname("Jose da Silva")
//                .email("josedasilva@email.com")
//                .birthdate(ApiDateRequest().date(Date()))
//                .taxDocument(TaxDocumentRequest.cpf("22222222222"))
//                .phone(PhoneRequest().setAreaCode("11").setNumber("55443322"))
//                .shippingAddressRequest(ShippingAddressRequest()
//                        .street("Avenida Faria Lima")
//                        .streetNumber("3064")
//                        .complement("12 andar")
//                        .city("São Paulo")
//                        .state("SP")
//                        .district("Itaim")
//                        .country("BRA")
//                        .zipCode("01452-000")
//                )
//        )
//
//    }



//
//val postOrderUri = "${props.services.external.wirecard.uri}${props.services.external.wirecard.uri}"
//
//val client = HttpClient.newBuilder().build();
//val request = HttpRequest.newBuilder()
//        .uri(URI.create(postOrderUri))
//        .
//        .build()
//val response = client.send(request, HttpResponse.BodyHandlers.ofString());
//println(response.body())
//return Pair(
//order.copy(
//payment = order.payment.copy(
//processor = PROCESSOR_NAME,
//publishedAt = LocalDateTime.now()
//)
//),
//order)