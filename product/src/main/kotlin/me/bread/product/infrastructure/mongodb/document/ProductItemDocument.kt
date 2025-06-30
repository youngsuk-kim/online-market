package me.bread.product.infrastructure.mongodb.document

import me.bread.product.domain.enums.ProductOption

/**
 * 상품 아이템 문서
 * MongoDB에 저장되는 상품 아이템 문서 클래스
 */
data class ProductItemDocument(
    val id: String,
    val optionKey: ProductOption,
    val optionValue: String,
    val stock: Int
)
