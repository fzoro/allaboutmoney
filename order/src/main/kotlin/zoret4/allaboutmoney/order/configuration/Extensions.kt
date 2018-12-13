package zoret4.allaboutmoney.order.configuration

import java.util.*

fun <T> Optional<T>.unwrap(): T? = orElse(null)

fun String.toUUID() = UUID.fromString(this)