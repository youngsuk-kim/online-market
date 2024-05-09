package me.bread.order.presentation.rest.request

import java.math.BigDecimal

data class PaymentRequest(val orderId: Long, val paymentKey: String, val amount: BigDecimal)
