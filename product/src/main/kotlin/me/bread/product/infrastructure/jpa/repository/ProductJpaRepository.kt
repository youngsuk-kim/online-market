package me.bread.product.infrastructure.jpa.repository

import me.bread.product.infrastructure.jpa.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<ProductEntity, Long>
