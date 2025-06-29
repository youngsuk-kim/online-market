package me.bread.product.domain.repository

import me.bread.product.domain.entity.Product

/**
 * 상품 저장소 인터페이스
 * 상품 엔티티에 대한 영속성 작업을 정의하는 인터페이스
 */
interface ProductRepository {
    /**
     * ID로 상품 조회
     * 지정된 ID에 해당하는 상품을 조회한다
     *
     * @param id 조회할 상품의 ID
     * @return 조회된 상품 또는 null(상품이 없는 경우)
     */
    fun findById(id: Long): Product?

    /**
     * 상품 저장
     * 상품 엔티티를 저장소에 저장한다
     *
     * @param product 저장할 상품 엔티티
     */
    fun save(product: Product)
}
