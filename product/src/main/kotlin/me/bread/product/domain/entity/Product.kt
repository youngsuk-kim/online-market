package me.bread.product.domain.entity

import java.math.BigDecimal

class Product(
    val id: String,
    var name: String,
    var price: BigDecimal,
    val items: MutableSet<ProductItem> = mutableSetOf(),
) {
    fun decreaseStock(itemId: String): Product {
        this.items.find { productItem -> productItem.id == itemId }
            ?.decrease()
        return this
    }

    fun stock(itemId: String): Int? {
        return this.items.find { productItem -> productItem.id == itemId }?.stock
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (id != other.id) return false
        if (name != other.name) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + price.hashCode()
        return result
    }
}
