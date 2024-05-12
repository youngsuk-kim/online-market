package me.bread.product.domain.entity

import java.math.BigDecimal

class Product(
    val id: Long = 0,
    var name: String,
    var price: BigDecimal,
    val productItems: List<ProductItem> = emptyList(),
) {
    fun decreaseStock(itemId: Long) {
        this.productItems.find { productItem -> productItem.id == itemId }
            ?.decrease()
    }

    fun stock(itemId: Long): Int? {
        return this.productItems.find { productItem -> productItem.id == itemId }?.stock
    }
}
