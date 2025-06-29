package me.bread.product.infrastructure.jpa.repository

import me.bread.product.domain.entity.Product
import me.bread.product.domain.repository.ProductRepository
import me.bread.product.mapper.ProductItemMapper
import me.bread.product.mapper.ProductMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

/**
 * 상품 저장소 어댑터
 * 도메인 계층의 ProductRepository 인터페이스를 JPA 구현체에 연결하는 어댑터
 * 도메인 모델과 JPA 엔티티 간의 변환을 처리한다
 */
@Repository
class ProductRepositoryAdapter(
    private val productJpaRepository: ProductJpaRepository,
    private val productItemJpaRepository: ProductItemJpaRepository,
) : ProductRepository {
    /**
     * ID로 상품 조회
     * JPA 저장소에서 상품을 조회하고 도메인 모델로 변환한다
     * 상품 아이템도 함께 조회하여 상품 객체에 추가한다
     *
     * @param id 조회할 상품의 ID
     * @return 조회된 상품 도메인 객체 또는 null(상품이 없는 경우)
     */
    override fun findById(id: Long): Product? {
        val productEntity = productJpaRepository.findByIdOrNull(id) ?: return null
        val product = ProductMapper.toDomain(productEntity)

        val productItems = productItemJpaRepository.findByProductEntityId(product.id)
            .map(ProductItemMapper::toDomain)

        product.productItems.addAll(productItems)

        return product
    }

    /**
     * 상품 저장
     * 도메인 모델을 JPA 엔티티로 변환하여 저장한다
     * 상품과 관련된 상품 아이템도 함께 저장한다
     *
     * @param product 저장할 상품 도메인 객체
     */
    override fun save(product: Product) {
        val productEntity = ProductMapper.toEntity(product)
        productJpaRepository.save(productEntity)
        product.productItems.forEach {
            productItemJpaRepository.save(ProductItemMapper.toEntity(it, productEntity))
        }
    }
}
