package me.bread.product.infrastructure.mongodb.repository

import me.bread.product.infrastructure.mongodb.document.ProductDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

/**
 * 상품 몽고 커스텀 저장소 구현체
 * 페이징 및 검색 조건을 지원하는 커스텀 쿼리 메서드 구현
 */
@Repository
class ProductMongoCustomRepositoryImpl(
    private val mongoTemplate: MongoTemplate
) : ProductMongoCustomRepository {

    override fun findBySearchConditions(
        name: String?,
        minPrice: Double?,
        maxPrice: Double?,
        pageable: Pageable
    ): Page<ProductDocument> {
        val query = Query()

        // 검색 조건 적용
        val criteria = mutableListOf<Criteria>()

        // 이름 검색 조건 (부분 일치)
        name?.let {
            if (it.isNotBlank()) {
                criteria.add(Criteria.where("name").regex(".*$it.*", "i"))
            }
        }

        // 가격 범위 검색 조건
        val priceCriteria = mutableListOf<Criteria>()
        minPrice?.let {
            priceCriteria.add(Criteria.where("price").gte(it))
        }
        maxPrice?.let {
            priceCriteria.add(Criteria.where("price").lte(it))
        }

        if (priceCriteria.isNotEmpty()) {
            criteria.add(Criteria().andOperator(*priceCriteria.toTypedArray()))
        }

        // 모든 조건 결합
        if (criteria.isNotEmpty()) {
            query.addCriteria(Criteria().andOperator(*criteria.toTypedArray()))
        }

        // 전체 개수 조회
        val total = mongoTemplate.count(query, ProductDocument::class.java)

        // 페이징 적용
        query.with(pageable)

        // 결과 조회
        val content = mongoTemplate.find(query, ProductDocument::class.java)

        return PageImpl(content, pageable, total)
    }
}
