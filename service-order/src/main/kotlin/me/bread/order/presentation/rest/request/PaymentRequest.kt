package me.bread.order.presentation.rest.request

data class PaymentRequest(val paymentKey: String, val orderId: String, val amount: String)
