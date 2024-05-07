package me.bread.order.application.external

interface AuthApi {
    fun fetchCustomerIdBy(token: String): Long
}
