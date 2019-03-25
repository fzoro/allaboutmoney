package zoret4.allaboutmoney.order.model.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

/**
 * Not used as of now, but should be added
 */
data class Tracer(
        @LastModifiedDate val createdAt: Instant,
        @CreatedDate val updatedAt: Instant,
        val requestId: String = "todo",
        val createdBy: String = "todo",
        val updatedBy: String = createdBy,
        val origin: String = "todo"
)