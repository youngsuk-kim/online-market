package me.bread.order.domain.entity

import java.math.BigDecimal

data class PaymentItem(
    val productId: Long,
    val productName: String,
    val productPrice: BigDecimal,
    val quantity: Long,
)
