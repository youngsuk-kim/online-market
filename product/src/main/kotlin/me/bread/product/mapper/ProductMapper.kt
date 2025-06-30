package me.bread.product.mapper

import me.bread.product.domain.entity.Product
import me.bread.product.domain.entity.ProductItem
import me.bread.product.infrastructure.mongodb.document.ProductDocument
import me.bread.product.infrastructure.mongodb.document.ProductItemDocument

object ProductMapper {

    fun toDocument(productItem: ProductItem): ProductItemDocument {
        return ProductItemDocument(
            id = productItem.id,
            optionKey = productItem.optionKey,
            optionValue = productItem.optionValue,
            stock = productItem.stock,
        )
    }

    fun toDomain(productItemDocument: ProductItemDocument): ProductItem {
        return ProductItem(
            id = productItemDocument.id,
            optionKey = productItemDocument.optionKey,
            optionValue = productItemDocument.optionValue,
            stock = productItemDocument.stock,
        )
    }

    fun toDomain(productDocument: ProductDocument): Product {
        return Product(
            id = productDocument.id,
            name = productDocument.name,
            price = productDocument.price,
            items = productDocument.items.map { toDomain(it) }.toMutableSet(),
        )
    }

    fun toDocument(product: Product): ProductDocument {
        return ProductDocument(
            id = product.id,
            name = product.name,
            price = product.price,
            items = product.items.map { toDocument(it) }.toMutableSet(),
        )
    }
}
