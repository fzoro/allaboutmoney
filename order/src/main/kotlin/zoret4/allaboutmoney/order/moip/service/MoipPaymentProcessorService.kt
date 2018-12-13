package zoret4.allaboutmoney.order.moip.service

import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.service.contracts.PaymentProcessorService
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.LocalDateTime

@Service
class MoipPaymentProcessorService : PaymentProcessorService {

    companion object {
        const val PROCESSOR_NAME: String = "MOIP"
    }

    override fun processPaymentCreditCard(order: Order): Pair<Order, Order> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun processPaymentBarCode(order: Order): Pair<Order, Order> {

        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
                .uri(URI.create("https://google.com"))
                .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        println(response.body())
        return Pair(
                order.copy(
                        payment = order.payment.copy(
                                processor = PROCESSOR_NAME,
                                publishedAt = LocalDateTime.now()
                        )
                ),
                order)
    }

}