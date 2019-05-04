package zoret4.allaboutmoney.order.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun <T> Optional<T>.unwrap(): T? = orElse(null)

fun String.toDigit(): Int = this.replace(Regex("[^\\d]"), "").toInt()
fun String.toUUID(): UUID = UUID.fromString(this)
fun LocalDate.toDate(): Date = Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
fun Any.toJsonWithMapper(objectMapper: ObjectMapper) = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this)!!

inline fun <reified R : Any> R.logger(): Logger = LoggerFactory.getLogger(this::class.java.name.substringBefore("\$Companion"))

