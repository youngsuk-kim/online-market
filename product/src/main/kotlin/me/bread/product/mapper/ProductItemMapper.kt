package me.bread.product.mapper

import me.bread.product.domain.entity.ProductItem
import me.bread.product.infrastructure.jpa.entity.ProductEntity
import me.bread.product.infrastructure.jpa.entity.ProductItemEntity

object ProductItemMapper {

    fun toEntity(item: ProductItem, productEntity: ProductEntity): ProductItemEntity {
        return ProductItemEntity(
            id = item.id,
            productEntity = productEntity,
            optionKey = item.optionKey,
            optionValue = item.optionValue,
            stock = item.stock,
        )
    }
}
