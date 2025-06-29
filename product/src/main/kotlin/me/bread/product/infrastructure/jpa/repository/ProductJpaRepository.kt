package me.bread.product.infrastructure.jpa.repository

import me.bread.product.infrastructure.jpa.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 상품 JPA 저장소
 * 상품 엔티티에 대한 데이터베이스 작업을 처리하는 Spring Data JPA 인터페이스
 * 기본적인 CRUD 작업은 JpaRepository에서 상속받아 사용한다
 */
interface ProductJpaRepository : JpaRepository<ProductEntity, Long>
