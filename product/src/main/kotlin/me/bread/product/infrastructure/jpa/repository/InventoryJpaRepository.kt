package me.bread.product.infrastructure.jpa.repository

import me.bread.product.infrastructure.jpa.entity.InventoryEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 재고 JPA 저장소
 * 재고 엔티티에 대한 데이터베이스 작업을 처리하는 Spring Data JPA 인터페이스
 */
interface InventoryJpaRepository : JpaRepository<InventoryEntity, Long> {
    /**
     * SKU로 재고 조회
     * 지정된 SKU에 해당하는 재고를 조회한다
     *
     * @param sku 상품 아이템 SKU
     * @return 해당 상품 아이템의 재고 엔티티
     */
    fun findBySku(sku: String): InventoryEntity?
}
