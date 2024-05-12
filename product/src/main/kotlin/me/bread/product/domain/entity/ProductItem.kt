package me.bread.product.domain.entity

import me.bread.product.domain.enums.ProductOption

class ProductItem(
    val id: Long = 0,
    val optionKey: ProductOption,
    val optionValue: String,
    var stock: Int,
) {
    fun decrease() {
        stock -= 1
    }
}
