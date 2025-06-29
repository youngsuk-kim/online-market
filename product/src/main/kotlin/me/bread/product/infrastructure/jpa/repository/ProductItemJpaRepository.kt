package me.bread.product.infrastructure.jpa.repository

import me.bread.product.infrastructure.jpa.entity.ProductItemEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 상품 아이템 JPA 저장소
 * 상품 아이템 엔티티에 대한 데이터베이스 작업을 처리하는 Spring Data JPA 인터페이스
 */
interface ProductItemJpaRepository : JpaRepository<ProductItemEntity, Long> {
    /**
     * 상품 ID로 상품 아이템 목록 조회
     * 지정된 상품 ID에 속한 모든 상품 아이템을 조회한다
     *
     * @param productEntityId 상품 엔티티 ID
     * @return 해당 상품에 속한 상품 아이템 엔티티 목록
     */
    fun findByProductEntityId(productEntityId: Long): List<ProductItemEntity>
}
