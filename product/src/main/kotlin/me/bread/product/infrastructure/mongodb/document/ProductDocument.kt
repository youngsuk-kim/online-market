package me.bread.product.infrastructure.mongodb.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

/**
 * 상품 문서
 * MongoDB에 저장되는 상품 문서 클래스
 */
@Document(collection = "products")
data class ProductDocument(
    @Id
    val id: String,
    val name: String,
    val price: BigDecimal,
    val items: MutableSet<ProductItemDocument> = mutableSetOf()
)

