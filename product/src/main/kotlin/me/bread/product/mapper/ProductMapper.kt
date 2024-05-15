package me.bread.product.mapper

import me.bread.product.domain.entity.Product
import me.bread.product.infrastructure.jpa.entity.ProductEntity

object ProductMapper {

    fun toEntity(product: Product): ProductEntity {
        return ProductEntity(
            id = product.id,
            name = product.name,
            price = product.price,
        )
    }

    fun toDomain(productEntity: ProductEntity): Product {
        return Product(
            id = productEntity.id,
            name = productEntity.name,
            price = productEntity.price,
        )
    }
}
