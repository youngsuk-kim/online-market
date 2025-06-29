package me.bread.product.application.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.bread.product.domain.entity.Product
import me.bread.product.domain.repository.ProductRepository
import me.bread.product.infrastructure.jpa.builder.ProductBuilder
import me.bread.product.presentation.support.error.ErrorType
import me.bread.product.presentation.support.error.RestException
import java.math.BigDecimal

class ProductServiceTest : StringSpec({
    // 의존성 목 객체
    val productRepository = mockk<ProductRepository>()

    val productService = ProductService(productRepository)

    // 테스트 데이터
    val productId = 1L
    val product = ProductBuilder.aProduct()
        .id(productId)
        .name("테스트 상품")
        .price("100.00")
        .build()

    "상품이 저장소에 존재하는 경우, ID로 상품을 찾을 때 상품을 반환해야 한다" {
        // Given
        every { productRepository.findById(productId) } returns product

        // When
        val result = productService.findById(productId)

        // Then
        result shouldBeSameInstanceAs product
        verify { productRepository.findById(productId) }
    }

    "상품이 저장소에 존재하지 않는 경우, ID로 상품을 찾을 때 예외를 발생시켜야 한다" {
        // Given
        every { productRepository.findById(productId) } returns null

        // When & Then
        val exception = shouldThrow<RestException> {
            productService.findById(productId)
        }

        // Then
        exception.errorType shouldBe ErrorType.INVALID_ARG_ERROR
        verify { productRepository.findById(productId) }
    }

    "저장할 상품이 주어진 경우, 상품을 저장할 때 상품을 저장소에 저장해야 한다" {
        // Given
        every { productRepository.save(product) } returns Unit

        // When
        productService.save(product)

        // Then
        verify { productRepository.save(product) }
    }
})
