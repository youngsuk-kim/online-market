package me.bread.product.domain.entity

class Product(
    val id: Long = 0,
    var stock: Int,
) {
    fun decrease() {
        stock -= 1
    }
}
