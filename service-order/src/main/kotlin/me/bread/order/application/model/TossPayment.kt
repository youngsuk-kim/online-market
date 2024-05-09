package me.bread.order.application.model

import java.math.BigDecimal

data class TossPayment(
    val orderId: Long,
    val amount: BigDecimal,
)
