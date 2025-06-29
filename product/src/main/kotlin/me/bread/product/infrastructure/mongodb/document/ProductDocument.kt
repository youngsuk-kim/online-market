package me.bread.product.infrastructure.mongodb.document

import me.bread.product.domain.enums.ProductOption
import me.bread.product.domain.mongodb.ProductMongoDomain
import me.bread.product.domain.mongodb.ProductItemMongoDomain
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

/**
 * 상품 문서
 * MongoDB에 저장되는 상품 문서 클래스
 */
@Document(collection = "products")
data class ProductDocument(
    @Id
    val id: String,
    val name: String,
    val price: BigDecimal,
    val items: List<ProductItemDocument> = emptyList()
) {
    companion object {
        fun fromMongoDomain(mongoDomain: ProductMongoDomain): ProductDocument {
            return ProductDocument(
                id = mongoDomain.id,
                name = mongoDomain.name,
                price = mongoDomain.price,
                items = mongoDomain.items.map { ProductItemDocument.fromMongoDomain(it) }
            )
        }
    }

    fun toMongoDomain(): ProductMongoDomain {
        return ProductMongoDomain(
            id = id,
            name = name,
            price = price,
            items = items.map { it.toMongoDomain() }
        )
    }
}

/**
 * 상품 아이템 문서
 * MongoDB에 저장되는 상품 아이템 문서 클래스
 */
data class ProductItemDocument(
    val id: String,
    val optionKey: ProductOption,
    val optionValue: String,
    val stock: Int
) {
    companion object {
        fun fromMongoDomain(mongoDomain: ProductItemMongoDomain): ProductItemDocument {
            return ProductItemDocument(
                id = mongoDomain.id,
                optionKey = mongoDomain.optionKey,
                optionValue = mongoDomain.optionValue,
                stock = mongoDomain.stock
            )
        }
    }

    fun toMongoDomain(): ProductItemMongoDomain {
        return ProductItemMongoDomain(
            id = id,
            optionKey = optionKey,
            optionValue = optionValue,
            stock = stock
        )
    }
}
