package me.bread.order.application.model

data class Customer(
    val token: String,
    val customerEmail: String,
    val customerName: String,
    val customerPostalCode: String,
    val customerAddressDetail: String,
)
