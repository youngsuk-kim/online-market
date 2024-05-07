package me.bread.order.domain.entity

data class Delivery(
    val orderId: Long,
    val customerEmail: String,
    val customerName: String,
    val customerPostalCode: String,
    val customerDestination: String,
)
