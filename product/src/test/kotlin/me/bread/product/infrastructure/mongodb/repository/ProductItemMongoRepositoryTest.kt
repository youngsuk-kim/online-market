package me.bread.product.infrastructure.mongodb.repository

import me.bread.product.annotation.MongoTest
import me.bread.product.domain.enums.ProductOption
import me.bread.product.infrastructure.mongodb.builder.ProductDocumentBuilder
import me.bread.product.infrastructure.mongodb.builder.ProductItemDocumentBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@MongoTest
class ProductItemMongoRepositoryTest {
    @Autowired
    private lateinit var productMongoRepository: ProductMongoRepository

    @BeforeEach
    fun setUp() {
        // 테스트 전에 모든 데이터 삭제
        productMongoRepository.deleteAll()
    }

    @Test
    fun `상품 아이템을 저장하고 ID로 조회할 수 있어야 한다`() {
        // Given
        val productItem = ProductItemDocumentBuilder.anItem()
            .id("1")
            .optionKey(ProductOption.COLOR)
            .optionValue("빨간색")
            .stock(10)
            .build()

        val product = ProductDocumentBuilder.aProduct()
            .id("1")
            .name("테스트 상품")
            .price("10000.00")
            .items(mutableSetOf(productItem))
            .build()

        // When
        productMongoRepository.save(product)
        val foundProduct = productMongoRepository.findById("1").orElse(null)

        // Then
        assertNotNull(foundProduct)
        assertEquals(1, foundProduct.items.size)

        val foundItem = foundProduct.items.first()
        assertEquals("1", foundItem.id)
        assertEquals(ProductOption.COLOR, foundItem.optionKey)
        assertEquals("빨간색", foundItem.optionValue)
        assertEquals(10, foundItem.stock)
    }

    @Test
    fun `상품 ID로 해당 상품의 모든 아이템을 조회할 수 있어야 한다`() {
        // Given
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

        val product = ProductDocumentBuilder.aProduct()
            .id("1")
            .name("테스트 상품")
            .price("10000.00")
            .items(mutableSetOf(productItem1, productItem2))
            .build()

        productMongoRepository.save(product)

        // When
        val foundProduct = productMongoRepository.findById("1").orElse(null)

        // Then
        assertNotNull(foundProduct)
        assertEquals(2, foundProduct.items.size)

        // 아이템 내용 검증
        val sortedItems = foundProduct.items.sortedBy { it.optionValue }
        assertEquals(ProductOption.SIZE, sortedItems[0].optionKey)
        assertEquals("대형", sortedItems[0].optionValue)
        assertEquals(5, sortedItems[0].stock)

        assertEquals(ProductOption.COLOR, sortedItems[1].optionKey)
        assertEquals("빨간색", sortedItems[1].optionValue)
        assertEquals(10, sortedItems[1].stock)
    }

    @Test
    fun `여러 상품에 대한 아이템을 저장하고 특정 상품의 아이템만 조회할 수 있어야 한다`() {
        // Given
        // 첫 번째 상품
        val productItem1 = ProductItemDocumentBuilder.anItem()
            .id("1")
            .optionKey(ProductOption.COLOR)
            .optionValue("빨간색")
            .stock(10)
            .build()

        val product1 = ProductDocumentBuilder.aProduct()
            .id("1")
            .name("테스트 상품 1")
            .price("10000.00")
            .items(mutableSetOf(productItem1))
            .build()

        // 두 번째 상품
        val productItem2 = ProductItemDocumentBuilder.anItem()
            .id("2")
            .optionKey(ProductOption.COLOR)
            .optionValue("파란색")
            .stock(20)
            .build()

        val productItem3 = ProductItemDocumentBuilder.anItem()
            .id("3")
            .optionKey(ProductOption.SIZE)
            .optionValue("중형")
            .stock(15)
            .build()

        val product2 = ProductDocumentBuilder.aProduct()
            .id("2")
            .name("테스트 상품 2")
            .price("20000.00")
            .items(mutableSetOf(productItem2, productItem3))
            .build()

        productMongoRepository.save(product1)
        productMongoRepository.save(product2)

        // When
        // 첫 번째 상품의 아이템 조회
        val foundProduct1 = productMongoRepository.findById("1").orElse(null)

        // 두 번째 상품의 아이템 조회
        val foundProduct2 = productMongoRepository.findById("2").orElse(null)

        // Then
        // 첫 번째 상품 검증
        assertNotNull(foundProduct1)
        assertEquals(1, foundProduct1.items.size)
        assertEquals(ProductOption.COLOR, foundProduct1.items.first().optionKey)
        assertEquals("빨간색", foundProduct1.items.first().optionValue)
        assertEquals(10, foundProduct1.items.first().stock)

        // 두 번째 상품 검증
        assertNotNull(foundProduct2)
        assertEquals(2, foundProduct2.items.size)

        // 두 번째 상품의 아이템 내용 검증
        // 아이템을 옵션 키로 정렬하여 검증
        val itemsByKey = foundProduct2.items.associateBy { it.optionKey }

        // COLOR 옵션 검증
        val colorItem = itemsByKey[ProductOption.COLOR]
        assertNotNull(colorItem)
        assertEquals("파란색", colorItem!!.optionValue)
        assertEquals(20, colorItem.stock)

        // SIZE 옵션 검증
        val sizeItem = itemsByKey[ProductOption.SIZE]
        assertNotNull(sizeItem)
        assertEquals("중형", sizeItem!!.optionValue)
        assertEquals(15, sizeItem.stock)
    }
}
