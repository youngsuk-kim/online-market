package me.bread.product.infrastructure.mongodb.adapter

import me.bread.product.domain.entity.Product
import me.bread.product.domain.repository.ProductRepository
import me.bread.product.infrastructure.mongodb.document.ProductDocument
import me.bread.product.infrastructure.mongodb.repository.ProductMongoRepository
import me.bread.product.mapper.ProductMapper
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

/**
 * 상품 몽고 어댑터
 * MongoDB 저장소를 도메인 저장소 인터페이스에 맞게 변환하는 어댑터
 * 도메인 엔티티와 MongoDB 도메인 모델 간의 변환을 담당
 */
@Repository
@Primary
class ProductMongoAdapter(
    private val productMongoRepository: ProductMongoRepository,
) : ProductRepository {

    override fun findById(id: String): Product? {
        val productDocument =
            productMongoRepository.findById(id).orElse(null) ?: return null
        return ProductMapper.toDomain(productDocument)
    }

    override fun save(product: Product): Product {
        val productDocument = ProductMapper.toDocument(product)
        productMongoRepository.save(productDocument)
        return ProductMapper.toDomain(productDocument)
    }

    override fun findByNameContainingIgnoreCase(name: String): List<ProductDocument> {
        return productMongoRepository.findByNameContainingIgnoreCase(name)
    }

    override fun findBySearchConditions(
        name: String?,
        minPrice: Double?,
        maxPrice: Double?,
        pageable: Pageable,
    ): Page<Product> {
        return productMongoRepository.findBySearchConditions(
            name = name,
            minPrice = minPrice,
            maxPrice = maxPrice,
            pageable = pageable,
        ).map { product -> ProductMapper.toDomain(product) }
    }
}
