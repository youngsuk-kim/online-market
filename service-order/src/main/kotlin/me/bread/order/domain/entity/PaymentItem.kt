package me.bread.order.domain.entity

import java.math.BigDecimal

data class PaymentItem(
    val productId: Long,
    val name: String,
    val price: BigDecimal,
    val quantity: Long,
)
