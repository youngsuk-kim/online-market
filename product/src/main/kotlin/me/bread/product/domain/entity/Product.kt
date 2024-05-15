package me.bread.product.domain.entity

import java.math.BigDecimal

class Product(
    val id: Long = 0,
    var name: String,
    var price: BigDecimal,
    val productItems: MutableList<ProductItem> = mutableListOf(),
) {
    fun decreaseStock(itemId: Long): Product {
        this.productItems.find { productItem -> productItem.id == itemId }
            ?.decrease()
        return this
    }

    fun stock(itemId: Long): Int? {
        return this.productItems.find { productItem -> productItem.id == itemId }?.stock
    }
}
