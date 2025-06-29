package me.bread.product.infrastructure.mongodb.document

import me.bread.product.domain.entity.Product
import me.bread.product.domain.entity.ProductItem
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
        fun fromDomain(product: Product): ProductDocument {
            return ProductDocument(
                id = product.id.toString(),
                name = product.name,
                price = product.price,
                items = product.productItems.map { ProductItemDocument.fromDomain(it) }
            )
        }
    }

    fun toDomain(): Product {
        val product = Product(
            id = id.toLongOrNull() ?: 0,
            name = name,
            price = price
        )

        product.productItems.addAll(items.map { it.toDomain() })

        return product
    }
}

/**
 * 상품 아이템 문서
 * MongoDB에 저장되는 상품 아이템 문서 클래스
 */
data class ProductItemDocument(
    val id: String,
    val optionKey: String,
    val optionValue: String,
    val stock: Int
) {
    companion object {
        fun fromDomain(productItem: ProductItem): ProductItemDocument {
            return ProductItemDocument(
                id = productItem.id.toString(),
                optionKey = productItem.optionKey.name,
                optionValue = productItem.optionValue,
                stock = productItem.stock
            )
        }
    }

    fun toDomain(): ProductItem {
        return ProductItem(
            id = id.toLongOrNull() ?: 0,
            optionKey = me.bread.product.domain.enums.ProductOption.valueOf(optionKey),
            optionValue = optionValue,
            stock = stock
        )
    }
}
