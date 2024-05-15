package me.bread.product.application.service

import me.bread.product.domain.entity.Product
import me.bread.product.domain.repository.ProductRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ProductService(
    private val productRepository: ProductRepository,
) {

    @Transactional(readOnly = true)
    fun findById(id: Long) = productRepository.findById(id)

    @Transactional
    fun save(product: Product) {
        productRepository.save(product)
    }
}
