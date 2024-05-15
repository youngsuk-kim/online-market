package me.bread.product.infrastructure.jpa.repository

import me.bread.product.domain.entity.Product
import me.bread.product.domain.repository.ProductRepository
import me.bread.product.mapper.ProductItemMapper
import me.bread.product.mapper.ProductMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryAdapter(
    private val productJpaRepository: ProductJpaRepository,
    private val productItemJpaRepository: ProductItemJpaRepository,
) : ProductRepository {
    override fun findById(id: Long): Product? {
        val productEntity = productJpaRepository.findByIdOrNull(id) ?: return null
        val product = ProductMapper.toDomain(productEntity)

        val productItems = productItemJpaRepository.findByProductEntityId(product.id)
            .map(ProductItemMapper::toDomain)

        product.productItems.addAll(productItems)

        return product
    }

    override fun save(product: Product) {
        val productEntity = ProductMapper.toEntity(product)
        productJpaRepository.save(productEntity)
        product.productItems.forEach {
            productItemJpaRepository.save(ProductItemMapper.toEntity(it, productEntity))
        }
    }
}
