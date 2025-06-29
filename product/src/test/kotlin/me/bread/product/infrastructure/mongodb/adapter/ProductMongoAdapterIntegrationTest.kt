package me.bread.product.infrastructure.mongodb.adapter

import me.bread.product.annotation.MongoTest
import me.bread.product.domain.enums.ProductOption
import me.bread.product.domain.mongodb.ProductMongoDomain
import me.bread.product.domain.mongodb.ProductItemMongoDomain
import me.bread.product.infrastructure.mongodb.builder.ProductDocumentBuilder
import me.bread.product.infrastructure.mongodb.builder.ProductItemDocumentBuilder
import me.bread.product.infrastructure.mongodb.repository.ProductMongoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import java.math.BigDecimal

@MongoTest
@Import(ProductMongoAdapter::class)
class ProductMongoAdapterIntegrationTest {
    @Autowired
    private lateinit var productMongoAdapter: ProductMongoAdapter

    @Autowired
    private lateinit var productMongoRepository: ProductMongoRepository

    @BeforeEach
    fun setUp() {
        // 테스트 전에 모든 데이터 삭제
        productMongoRepository.deleteAll()
    }

    @Test
    fun `상품을 저장하면 ID가 유지되어야 한다`() {
        // Given
        val productName = "테스트 상품"
        val productPrice = BigDecimal("10000.00")

        val product = ProductMongoDomain(
            id = "1",
            name = productName,
            price = productPrice,
            items = emptyList()
        ).toEntity()

        // When
        productMongoAdapter.save(product)

        // Then
        val savedDocument = productMongoRepository.findById("1").orElse(null)
        assertNotNull(savedDocument)
        assertEquals("1", savedDocument.id)
        assertEquals(productName, savedDocument.name)
        assertEquals(productPrice, savedDocument.price)
    }

    @Test
    fun `상품과 상품 아이템을 함께 저장하면 모두 저장되어야 한다`() {
        // Given
        val productName = "테스트 상품"
        val productPrice = BigDecimal("10000.00")
        val optionValue = "빨간색"

        val productItemMongoDomain = ProductItemMongoDomain(
            id = "1",
            optionKey = ProductOption.COLOR,
            optionValue = optionValue,
            stock = 10
        )

        val productMongoDomain = ProductMongoDomain(
            id = "1",
            name = productName,
            price = productPrice,
            items = listOf(productItemMongoDomain)
        )

        val product = productMongoDomain.toEntity()

        // When
        productMongoAdapter.save(product)
        val savedDocument = productMongoRepository.findById("1").orElse(null)
        val savedProduct = productMongoAdapter.findById("1")

        // Then
        assertNotNull(savedDocument)
        assertNotNull(savedProduct)
        assertEquals(productName, savedProduct!!.name)
        assertEquals(productPrice, savedProduct.price)
        assertEquals(1, savedProduct.items.size)

        val savedItem = savedProduct.items.first()
        assertEquals(ProductOption.COLOR, savedItem.optionKey)
        assertEquals(optionValue, savedItem.optionValue)
        assertEquals(10, savedItem.stock)
    }

    @Test
    fun `ID로 상품을 조회하면 상품과 상품 아이템이 함께 조회되어야 한다`() {
        // Given
        val productName = "테스트 상품"
        val productPrice = BigDecimal("10000.00")

        val productItem1 = ProductItemDocumentBuilder.anItem()
            .id("1")
            .optionKey(ProductOption.COLOR)
            .optionValue("빨간색")
            .stock(10)
            .build()

        val productItem2 = ProductItemDocumentBuilder.anItem()
            .id("2")
            .optionKey(ProductOption.SIZE)
            .optionValue("대형")
            .stock(5)
            .build()

        val productDocument = ProductDocumentBuilder.aProduct()
            .id("1")
            .name(productName)
            .price(productPrice)
            .items(listOf(productItem1, productItem2))
            .build()

        // 저장 및 저장된 엔티티 확인
        productMongoRepository.save(productDocument)

        // When
        val foundProduct = productMongoAdapter.findById("1")

        // Then
        assertNotNull(foundProduct)
        assertEquals(productName, foundProduct!!.name)
        assertEquals(productPrice, foundProduct.price)
        assertEquals(2, foundProduct.items.size)

        // 상품 아이템 검증
        val items = foundProduct.items.sortedBy { it.optionValue }
        assertEquals(ProductOption.SIZE, items[0].optionKey)
        assertEquals("대형", items[0].optionValue)
        assertEquals(5, items[0].stock)

        assertEquals(ProductOption.COLOR, items[1].optionKey)
        assertEquals("빨간색", items[1].optionValue)
        assertEquals(10, items[1].stock)
    }

    @Test
    fun `존재하지 않는 ID로 상품을 조회하면 null을 반환해야 한다`() {
        // Given
        // No setup needed

        // When
        val foundProduct = productMongoAdapter.findById("9999")

        // Then
        assertNull(foundProduct)
    }
}
