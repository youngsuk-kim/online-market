package me.bread.product.infrastructure.mongodb.builder

import me.bread.product.domain.enums.ProductOption
import me.bread.product.infrastructure.mongodb.document.ProductDocument
import me.bread.product.infrastructure.mongodb.document.ProductItemDocument
import java.math.BigDecimal

/**
 * 상품 문서 빌더
 * 테스트용 ProductDocument 객체를 생성하는 빌더 클래스
 */
class ProductDocumentBuilder {
    private var id: String = "1"
    private var name: String = "테스트 상품"
    private var price: BigDecimal = BigDecimal("10000.00")
    private var items: MutableSet<ProductItemDocument> = mutableSetOf()

    fun id(id: String) = apply { this.id = id }
    fun id(id: Long) = apply { this.id = id.toString() }
    fun name(name: String) = apply { this.name = name }
    fun price(price: BigDecimal) = apply { this.price = price }
    fun price(price: String) = apply { this.price = BigDecimal(price) }
    fun items(items: MutableSet<ProductItemDocument>) = apply { this.items = items }

    fun build(): ProductDocument {
        return ProductDocument(
            id = id,
            name = name,
            price = price,
            items = items
        )
    }

    companion object {
        fun aProduct() = ProductDocumentBuilder()
    }
}

/**
 * 상품 아이템 문서 빌더
 * 테스트용 ProductItemDocument 객체를 생성하는 빌더 클래스
 */
class ProductItemDocumentBuilder {
    private var id: String = "1"
    private var optionKey: ProductOption = ProductOption.COLOR
    private var optionValue: String = "빨간색"
    private var stock: Int = 10

    fun id(id: String) = apply { this.id = id }
    fun id(id: Long) = apply { this.id = id.toString() }
    fun optionKey(optionKey: ProductOption) = apply { this.optionKey = optionKey }
    fun optionValue(optionValue: String) = apply { this.optionValue = optionValue }
    fun stock(stock: Int) = apply { this.stock = stock }

    fun build(): ProductItemDocument {
        return ProductItemDocument(
            id = id,
            optionKey = optionKey,
            optionValue = optionValue,
            stock = stock
        )
    }

    companion object {
        fun anItem() = ProductItemDocumentBuilder()
    }
}
