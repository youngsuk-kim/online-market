package me.bread.product.infrastructure.jpa.repository

import me.bread.product.infrastructure.jpa.entity.ProductItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductItemJpaRepository : JpaRepository<ProductItemEntity, Long> {
    fun findByProductEntityId(productEntityId: Long): List<ProductItemEntity>
}
