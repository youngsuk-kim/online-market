package me.bread.product.application.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.bread.product.domain.repository.ProductRepository
import me.bread.product.infrastructure.jpa.builder.ProductBuilder
import me.bread.product.infrastructure.mongodb.builder.ProductDocumentBuilder
import me.bread.product.presentation.support.error.ErrorType
import me.bread.product.presentation.support.error.ProductException
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.math.BigDecimal

class ProductServiceTest : StringSpec({
    // 의존성 목 객체
    val productRepository = mockk<ProductRepository>()

    val productService = ProductService(productRepository)

    // 테스트 데이터
    val productId = "TEST ITEM ID"
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
        val exception = shouldThrow<ProductException> {
            productService.findById(productId)
        }

        // Then
        exception.errorType shouldBe ErrorType.INVALID_ARG_ERROR
        verify { productRepository.findById(productId) }
    }

    "저장할 상품이 주어진 경우, 상품을 저장할 때 상품을 저장소에 저장해야 한다" {
        // Given
        every { productRepository.save(product) } returns product

        // When
        productService.save(product)

        // Then
        verify { productRepository.save(product) }
    }


    "검색 조건과 페이징으로 상품을 조회해야 되어야 한다" {
        // Given
        val name = "테스트"
        val minPrice = BigDecimal("10.00")
        val maxPrice = BigDecimal("100.00")
        val pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"))

        val productDocuments = listOf(
            ProductDocumentBuilder.aProduct().id("1").name("테스트 상품1").build(),
            ProductDocumentBuilder.aProduct().id("2").name("테스트 상품2").build()
        )

        val page = PageImpl(productDocuments, pageable, productDocuments.size.toLong())

        every {
            productRepository.findBySearchConditions(
                name = name,
                minPrice = minPrice.toDouble(),
                maxPrice = maxPrice.toDouble(),
                pageable = pageable
            )
        } returns page

        // When
        val result = productService.findBySearchConditions(
            name = name,
            minPrice = minPrice,
            maxPrice = maxPrice,
            pageable = pageable
        )

        // Then
        result.totalElements shouldBe 2L
        result.content.size shouldBe 2
        verify {
            productRepository.findBySearchConditions(
                name = name,
                minPrice = minPrice.toDouble(),
                maxPrice = maxPrice.toDouble(),
                pageable = pageable
            )
        }
    }

    "findByName 메서드는 이름으로 상품을 검색해야 한다" {
        // Given
        val name = "테스트"
        val productDocuments = listOf(
            ProductDocumentBuilder.aProduct().id("1").name("테스트 상품1").build(),
            ProductDocumentBuilder.aProduct().id("2").name("테스트 상품2").build()
        )

        every { productRepository.findByNameContainingIgnoreCase(name) } returns productDocuments

        // When
        val result = productService.findByName(name)

        // Then
        result.size shouldBe 2
        result[0].name shouldBe "테스트 상품1"
        result[1].name shouldBe "테스트 상품2"
        verify { productRepository.findByNameContainingIgnoreCase(name) }
    }
})
