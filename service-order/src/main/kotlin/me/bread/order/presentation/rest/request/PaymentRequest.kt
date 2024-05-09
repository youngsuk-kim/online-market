package me.bread.order.presentation.rest.request

data class PaymentRequest(val orderId: Long, val paymentKey: String, val amount: Long)
