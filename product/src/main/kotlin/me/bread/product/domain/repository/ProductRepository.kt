package me.bread.product.domain.repository

import me.bread.product.domain.entity.Product
import me.bread.product.infrastructure.mongodb.document.ProductDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

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
    fun findById(id: String): Product?

    /**
     * 상품 저장
     * 상품 엔티티를 저장소에 저장한다
     *
     * @param product 저장할 상품 엔티티
     * @return 저장된 상품 엔티티
     */
    fun save(product: Product): Product

    /**
     * 상품 이름으로 상품 찾기
     * @param name 상품 이름
     * @return 이름이 일치하는 상품 목록
     */
    fun findByNameContainingIgnoreCase(name: String): List<ProductDocument>

    /**
     * 페이징 및 검색 조건으로 상품 조회
     *
     * @param name 상품 이름 (부분 일치)
     * @param minPrice 최소 가격
     * @param maxPrice 최대 가격
     * @param pageable 페이징 정보
     * @return 페이징된 상품 목록
     */
    fun findBySearchConditions(
        name: String? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        pageable: Pageable
    ): Page<Product>
}
