package me.bread.product.infrastructure.mongodb.repository

import me.bread.product.infrastructure.mongodb.document.ProductDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * 상품 몽고 커스텀 저장소 인터페이스
 * 페이징 및 검색 조건을 지원하는 커스텀 쿼리 메서드를 정의
 */
interface ProductMongoCustomRepository {
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
    ): Page<ProductDocument>
}
