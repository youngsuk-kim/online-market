package me.bread.product.infrastructure.mongodb.builder

import me.bread.product.domain.entity.Product
import me.bread.product.domain.entity.ProductItem
import me.bread.product.domain.enums.ProductOption
import java.math.BigDecimal

class ProductBuilder {
    private var id: String = "TESTID"
    private var name: String = "테스트 상품"
    private var price: BigDecimal = BigDecimal("10000.00")
    private var productItems: MutableSet<ProductItem> = mutableSetOf()

    fun id(id: String) = apply { this.id = id }
    fun name(name: String) = apply { this.name = name }
    fun price(price: BigDecimal) = apply { this.price = price }
    fun price(price: String) = apply { this.price = BigDecimal(price) }
    fun addProductItem(productItem: ProductItem) = apply { this.productItems.add(productItem) }

    fun build(): Product {
        return Product(
            id = id,
            name = name,
            price = price,
            items = productItems
        )
    }

    companion object {
        fun aProduct() = ProductBuilder()
    }
}

class ProductItemBuilder {
    private var id: String = "TEST ITEM ID"
    private var optionKey: ProductOption = ProductOption.COLOR
    private var optionValue: String = "빨간색"
    private var stock: Int = 10

    fun id(id: String) = apply { this.id = id }
    fun stock(stock: Int) = apply { this.stock = stock }

    fun build(): ProductItem {
        return ProductItem(
            id = id,
            optionKey = optionKey,
            optionValue = optionValue,
            stock = stock
        )
    }

    companion object {
        fun anItem() = ProductItemBuilder()
    }
}
