package me.bread.product.infrastructure.mongodb.repository

import me.bread.product.infrastructure.mongodb.document.ProductDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * 상품 몽고 저장소
 * MongoDB에 저장된 상품 문서에 접근하는 저장소 인터페이스
 * 기본 CRUD 메서드는 MongoRepository에서 제공되며,
 * 커스텀 쿼리 메서드는 ProductMongoCustomRepository에서 제공됨
 */
@Repository
interface ProductMongoRepository : MongoRepository<ProductDocument, String>, ProductMongoCustomRepository {
    /**
     * 상품 이름으로 상품 찾기
     * @param name 상품 이름
     * @return 이름이 일치하는 상품 목록
     */
    fun findByNameContainingIgnoreCase(name: String): List<ProductDocument>
}
