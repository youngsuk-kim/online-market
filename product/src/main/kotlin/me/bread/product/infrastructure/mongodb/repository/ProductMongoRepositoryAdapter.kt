package me.bread.product.infrastructure.mongodb.repository

import me.bread.product.domain.entity.Product
import me.bread.product.domain.repository.ProductRepository
import me.bread.product.infrastructure.mongodb.document.ProductDocument
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

/**
 * 상품 몽고 저장소 어댑터
 * MongoDB 저장소를 도메인 저장소 인터페이스에 맞게 변환하는 어댑터
 */
@Repository
@Primary
class ProductMongoRepositoryAdapter(
    private val productMongoRepository: ProductMongoRepository
) : ProductRepository {

    override fun findById(id: Long): Product? {
        val productDocument = productMongoRepository.findById(id.toString()).orElse(null) ?: return null
        return productDocument.toDomain()
    }

    override fun save(product: Product) {
        val productDocument = ProductDocument.fromDomain(product)
        productMongoRepository.save(productDocument)
    }
}
