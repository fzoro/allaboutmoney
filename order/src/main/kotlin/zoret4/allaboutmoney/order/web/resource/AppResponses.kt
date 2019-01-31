package zoret4.allaboutmoney.order.web.resources

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun <T> orNotFound(body: T?): ResponseEntity<T> {
    return body
            ?.let {
                ResponseEntity(it, HttpStatus.OK)
            }
            ?: ResponseEntity.notFound().build()
}