package me.bread.order.application.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class TossPayment(
    val orderId: Long,
    val amount: BigDecimal,
    val requestedAt: LocalDateTime,
)
