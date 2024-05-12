package me.bread.product.domain.repository

import me.bread.product.domain.entity.Product

interface ProductRepository {
    fun findById(id: Long): Product?
}
