package me.bread.order.application.external

interface ProductApi {
    fun isProductQuantityEnough(itemId: Long): Boolean
}
