package zoret4.allaboutmoney.order.configuration

import com.mongodb.BasicDBObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun <T> Optional<T>.unwrap(): T? = orElse(null)

fun String.toUUID() = UUID.fromString(this)

//TODO check if the logger instance is corresponding to the caller
inline fun <reified R : Any> R.logger(): Logger =
        LoggerFactory.getLogger(this::class.java.name.substringBefore("\$Companion"))

fun LocalDate.toDate(): Date = Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())

/*
java 11 - http request


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
 */