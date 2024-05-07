package me.bread.order.application.external

interface DeliveryApi {
    fun fetchSurChargeArea(): List<String>
}
