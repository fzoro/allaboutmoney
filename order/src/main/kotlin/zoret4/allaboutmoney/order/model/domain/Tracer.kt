package zoret4.allaboutmoney.order.model.domain

import java.time.LocalDateTime

data class Tracer(
        val requestId: String,
        val createdBy: String,
        val updatedBy: String = createdBy,
        val createdAt: LocalDateTime = LocalDateTime.now(),
        val updatedAt: LocalDateTime = createdAt,
        val origin: String
)