package me.bread.product.infrastructure.jpa.builder

import me.bread.product.domain.enums.ProductOption
import me.bread.product.infrastructure.jpa.entity.ProductEntity
import me.bread.product.infrastructure.jpa.entity.ProductItemEntity
import java.math.BigDecimal

class ProductEntityBuilder {
    private var id: Long = 0
    private var name: String = "테스트 상품"
    private var price: BigDecimal = BigDecimal("10000.00")

    fun id(id: Long) = apply { this.id = id }
    fun name(name: String) = apply { this.name = name }
    fun price(price: BigDecimal) = apply { this.price = price }
    fun price(price: String) = apply { this.price = BigDecimal(price) }

    fun build(): ProductEntity {
        return ProductEntity(
            id = id,
            name = name,
            price = price
        )
    }

    companion object {
        fun aProduct() = ProductEntityBuilder()
    }
}

class ProductItemEntityBuilder {
    private var id: Long = 0
    private var productEntity: ProductEntity? = null
    private var optionKey: ProductOption = ProductOption.COLOR
    private var optionValue: String = "빨간색"
    private var stock: Int = 10

    fun id(id: Long) = apply { this.id = id }
    fun productEntity(productEntity: ProductEntity) = apply { this.productEntity = productEntity }
    fun optionKey(optionKey: ProductOption) = apply { this.optionKey = optionKey }
    fun optionValue(optionValue: String) = apply { this.optionValue = optionValue }
    fun stock(stock: Int) = apply { this.stock = stock }

    fun build(): ProductItemEntity {
        requireNotNull(productEntity) { "ProductEntity must be provided" }

        return ProductItemEntity(
            id = id,
            productEntity = productEntity!!,
            optionKey = optionKey,
            optionValue = optionValue,
            stock = stock
        )
    }

    companion object {
        fun anItem() = ProductItemEntityBuilder()
    }
}
