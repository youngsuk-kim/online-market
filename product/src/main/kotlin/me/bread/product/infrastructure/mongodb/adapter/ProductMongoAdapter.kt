package me.bread.product.infrastructure.mongodb.adapter

import me.bread.product.domain.entity.Product
import me.bread.product.domain.entity.ProductItem
import me.bread.product.domain.mongodb.ProductItemMongoDomain
import me.bread.product.domain.mongodb.ProductMongoDomain
import me.bread.product.domain.repository.ProductRepository
import me.bread.product.infrastructure.mongodb.document.ProductDocument
import me.bread.product.infrastructure.mongodb.document.ProductItemDocument
import me.bread.product.infrastructure.mongodb.repository.ProductMongoRepository
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
    private val productMongoRepository: ProductMongoRepository
) : ProductRepository {

    override fun findById(id: String): Product? {
        val productDocument = productMongoRepository.findById(id.toString()).orElse(null) ?: return null

        // MongoDB 문서를 MongoDB 도메인 모델로 변환
        val productMongoDomain = ProductMongoDomain(
            id = productDocument.id,
            name = productDocument.name,
            price = productDocument.price,
            items = productDocument.items.map { item ->
                ProductItemMongoDomain(
                    id = item.id,
                    optionKey = item.optionKey,
                    optionValue = item.optionValue,
                    stock = item.stock
                )
            }
        )

        // MongoDB 도메인 모델을 도메인 엔티티로 변환
        return productMongoDomain.toEntity()
    }

    override fun save(product: Product): Product {
        // 도메인 엔티티를 MongoDB 도메인 모델로 변환
        val productMongoDomain = ProductMongoDomain.fromEntity(product)

        // MongoDB 도메인 모델을 MongoDB 문서로 변환
        val productDocument = ProductDocument(
            id = productMongoDomain.id,
            name = productMongoDomain.name,
            price = productMongoDomain.price,
            items = productMongoDomain.items.map { item ->
                ProductItemDocument(
                    id = item.id,
                    optionKey = item.optionKey,
                    optionValue = item.optionValue,
                    stock = item.stock
                )
            }
        )

        productMongoRepository.save(productDocument)

        return Product(
            id = productDocument.id,
            name = productDocument.name,
            price = productDocument.price,
            items = productDocument.items.map { item ->
                ProductItem(
                    id = item.id,
                    optionKey = item.optionKey,
                    optionValue = item.optionValue,
                    stock = item.stock
                )
            }.toMutableSet()
        )
    }

    override fun findByNameContainingIgnoreCase(name: String): List<ProductDocument> {
        return productMongoRepository.findByNameContainingIgnoreCase(name)
    }

    override fun findBySearchConditions(
        name: String?,
        minPrice: Double?,
        maxPrice: Double?,
        pageable: Pageable,
    ): Page<ProductDocument> {
        return productMongoRepository.findBySearchConditions(
            name = name,
            minPrice = minPrice,
            maxPrice = maxPrice,
            pageable = pageable
        )
            .map { product ->
                ProductDocument(
                    id = product.id,
                    name = product.name,
                    price = product.price,
                    items = product.items.map { item ->
                        ProductItemDocument(
                            id = item.id,
                            optionKey = item.optionKey,
                            optionValue = item.optionValue,
                            stock = item.stock
                        )
                    }
                )
            }
    }
}
