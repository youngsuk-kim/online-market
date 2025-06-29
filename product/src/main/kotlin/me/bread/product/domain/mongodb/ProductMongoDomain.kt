package me.bread.product.domain.mongodb

import me.bread.product.domain.entity.Product
import me.bread.product.domain.entity.ProductItem
import me.bread.product.domain.enums.ProductOption
import java.math.BigDecimal

/**
 * 상품 몽고 도메인
 * MongoDB에 저장되는 상품 도메인 클래스
 */
class ProductMongoDomain(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val items: List<ProductItemMongoDomain> = emptyList()
) {
    companion object {
        fun fromEntity(product: Product): ProductMongoDomain {
            return ProductMongoDomain(
                id = product.id.toString(),
                name = product.name,
                price = product.price,
                items = product.items.map { ProductItemMongoDomain.fromEntity(it) }
            )
        }
    }

    fun toEntity(): Product {
        val product = Product(
            id = id,
            name = name,
            price = price
        )

        product.items.addAll(items.map { it.toEntity() })

        return product
    }
}

/**
 * 상품 아이템 몽고 도메인
 * MongoDB에 저장되는 상품 아이템 도메인 클래스
 */
class ProductItemMongoDomain(
    val id: String,
    val optionKey: ProductOption,
    val optionValue: String,
    val stock: Int
) {
    companion object {
        fun fromEntity(productItem: ProductItem): ProductItemMongoDomain {
            return ProductItemMongoDomain(
                id = productItem.id.toString(),
                optionKey = productItem.optionKey,
                optionValue = productItem.optionValue,
                stock = productItem.stock
            )
        }
    }

    fun toEntity(): ProductItem {
        return ProductItem(
            id = id,
            optionKey = optionKey,
            optionValue = optionValue,
            stock = stock
        )
    }
}
