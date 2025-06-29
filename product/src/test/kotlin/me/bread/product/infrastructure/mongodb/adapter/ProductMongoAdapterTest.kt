package me.bread.product.infrastructure.mongodb.adapter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.bread.product.infrastructure.jpa.builder.ProductBuilder
import me.bread.product.infrastructure.mongodb.builder.ProductDocumentBuilder
import me.bread.product.infrastructure.mongodb.repository.ProductMongoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.Optional

class ProductMongoAdapterTest {
    // 의존성 목 객체
    private val productMongoRepository = mockk<ProductMongoRepository>()

    // 테스트 대상
    private val productMongoAdapter = ProductMongoAdapter(productMongoRepository)

    // 테스트 데이터
    private val productId = "1"
    private val productDocument = ProductDocumentBuilder.aProduct()
        .id(productId)
        .name("테스트 상품")
        .price("100.00")
        .build()

    private val product = ProductBuilder.aProduct()
        .id(productId)
        .name("테스트 상품")
        .price("100.00")
        .build()

    @Test
    fun ID로_상품을_조회한다() {
        // Given
        every { productMongoRepository.findById(productId) } returns Optional.of(productDocument)

        // When
        val result = productMongoAdapter.findById(productId)

        // Then
        assertNotNull(result)
        assertEquals(productId, result?.id)
        assertEquals("테스트 상품", result?.name)
        verify { productMongoRepository.findById(productId) }
    }

    @Test
    fun 상품이_없으면_null을_반환한다() {
        // Given
        every { productMongoRepository.findById(productId) } returns Optional.empty()

        // When
        val result = productMongoAdapter.findById(productId)

        // Then
        assertNull(result)
        verify { productMongoRepository.findById(productId) }
    }

    @Test
    fun 상품을_저장한다() {
        // Given
        every { productMongoRepository.save(any()) } returns productDocument

        // When
        productMongoAdapter.save(product)

        // Then
        verify { productMongoRepository.save(any()) }
    }
}
