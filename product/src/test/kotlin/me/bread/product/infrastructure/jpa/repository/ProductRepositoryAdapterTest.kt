package me.bread.product.infrastructure.jpa.repository

import me.bread.product.annotation.MySQLTest
import me.bread.product.domain.enums.ProductOption
import me.bread.product.infrastructure.jpa.builder.ProductBuilder
import me.bread.product.infrastructure.jpa.builder.ProductItemBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import java.math.BigDecimal

@MySQLTest
@Import(ProductRepositoryAdapter::class)
class ProductRepositoryAdapterTest {
    @Autowired
    private lateinit var productRepositoryAdapter: ProductRepositoryAdapter

    @Autowired
    private lateinit var productJpaRepository: ProductJpaRepository

    @Test
    fun `상품을 저장하면 ID가 생성되어야 한다`() {
        // Given
        val productName = "테스트 상품"
        val productPrice = BigDecimal("10000.00")

        val product = ProductBuilder.aProduct()
            .name(productName)
            .price(productPrice)
            .build()

        // When
        productRepositoryAdapter.save(product)

        // Then
        val savedEntity = productJpaRepository.findAll()
            .firstOrNull { it.name == productName && it.price == productPrice }

        assertNotNull(savedEntity)
        assertNotEquals(0L, savedEntity!!.id)
    }

    @Test
    fun `상품과 상품 아이템을 함께 저장하면 모두 저장되어야 한다`() {
        // Given
        val productName = "테스트 상품"
        val productPrice = BigDecimal("10000.00")
        val optionValue = "빨간색"

        val productItem = ProductItemBuilder.anItem()
            .optionKey(ProductOption.COLOR)
            .optionValue(optionValue)
            .stock(10)
            .build()

        val product = ProductBuilder.aProduct()
            .name(productName)
            .price(productPrice)
            .addProductItem(productItem)
            .build()

        // When
        productRepositoryAdapter.save(product)
        val savedEntity = productJpaRepository.findAll()
            .firstOrNull { it.name == productName && it.price == productPrice }
        val savedProduct = productRepositoryAdapter.findById(savedEntity!!.id)

        // Then
        assertNotNull(savedEntity)
        assertNotNull(savedProduct)
        assertEquals(productName, savedProduct!!.name)
        assertEquals(productPrice, savedProduct.price)
        assertEquals(1, savedProduct.productItems.size)

        val savedItem = savedProduct.productItems.first()
        assertEquals(ProductOption.COLOR, savedItem.optionKey)
        assertEquals(optionValue, savedItem.optionValue)
        assertEquals(10, savedItem.stock)
    }

    @Test
    fun `ID로 상품을 조회하면 상품과 상품 아이템이 함께 조회되어야 한다`() {
        // Given
        val productName = "테스트 상품"
        val productPrice = BigDecimal("10000.00")

        val productItem1 = ProductItemBuilder.anItem()
            .optionKey(ProductOption.COLOR)
            .optionValue("빨간색")
            .stock(10)
            .build()

        val productItem2 = ProductItemBuilder.anItem()
            .optionKey(ProductOption.SIZE)
            .optionValue("대형")
            .stock(5)
            .build()

        val product = ProductBuilder.aProduct()
            .name(productName)
            .price(productPrice)
            .addProductItem(productItem1)
            .addProductItem(productItem2)
            .build()

        // 저장 및 저장된 엔티티 확인
        productRepositoryAdapter.save(product)
        val savedEntity = productJpaRepository.findAll()
            .firstOrNull { it.name == productName && it.price == productPrice }
        assertNotNull(savedEntity)

        // When
        val foundProduct = productRepositoryAdapter.findById(savedEntity!!.id)

        // Then
        assertNotNull(foundProduct)
        assertEquals(productName, foundProduct!!.name)
        assertEquals(productPrice, foundProduct.price)
        assertEquals(2, foundProduct.productItems.size)

        // 상품 아이템 검증
        val items = foundProduct.productItems.sortedBy { it.optionValue }
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
        val foundProduct = productRepositoryAdapter.findById(9999L)

        // Then
        assertNull(foundProduct)
    }
}
