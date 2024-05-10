package me.bread.order.application.external

interface AuthApi {
    suspend fun fetchCustomerIdBy(token: String): Long
}
