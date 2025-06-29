package me.bread.product.mapper

import me.bread.product.domain.entity.Product
import me.bread.product.infrastructure.mongodb.document.ProductDocument

object ProductMapper {

    fun toDomain(productDocument: ProductDocument): Product {
        return Product(
            id = productDocument.id,
            name = productDocument.name,
            price = productDocument.price,
        )
    }
}
