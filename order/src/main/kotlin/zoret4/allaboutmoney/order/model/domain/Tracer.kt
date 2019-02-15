package zoret4.allaboutmoney.order.model.domain

import java.time.Instant

/**
 * Not used as of now, but should be added
 */
data class Tracer(
        val requestId: String,
        val createdBy: String,
        val updatedBy: String = createdBy,
        val createdAt: Instant = Instant.now(),
        val updatedAt: Instant = createdAt,
        val origin: String
)