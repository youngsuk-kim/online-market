package me.bread.product.application.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.bread.product.application.service.ProductService
import me.bread.product.infrastructure.mongodb.builder.ProductBuilder
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.math.BigDecimal

class DisplayProductUseCaseTest : StringSpec({
    // 의존성 목 객체
    val productService = mockk<ProductService>()

    // 테스트 대상
    val displayProductUseCase = DisplayProductUseCase(productService)

    // 테스트 데이터
    val productId = "TEST ITEM ID"
    val product = ProductBuilder.aProduct()
        .id(productId)
        .name("테스트 상품")
        .price("100.00")
        .build()

    val productList = listOf(
        ProductBuilder.aProduct().id("TEST ITEM ID 1").name("상품1").build(),
        ProductBuilder.aProduct().id("TEST ITEM ID 2").name("상품2").build(),
        ProductBuilder.aProduct().id("TEST ITEM ID 3").name("상품3").build()
    )

    "ID로 상품이 조회 되어야 한다" {
        // Given
        every { productService.findById(productId) } returns product

        // When
        val result = displayProductUseCase.execute(productId)

        // Then
        result shouldBe product
        verify { productService.findById(productId) }
    }

    "검색 조건과 페이징으로 상품이 조회 되어야 한다" {
        // Given
        val name = "테스트"
        val minPrice = BigDecimal("10.00")
        val maxPrice = BigDecimal("100.00")
        val pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"))
        val page = PageImpl(productList, pageable, productList.size.toLong())

        every {
            productService.findBySearchConditions(
                name = name,
                minPrice = minPrice,
                maxPrice = maxPrice,
                pageable = pageable
            )
        } returns page

        // When
        val result = displayProductUseCase.executeWithSearchConditions(
            name = name,
            minPrice = minPrice,
            maxPrice = maxPrice,
            pageable = pageable
        )

        // Then
        result shouldBe page
        verify {
            productService.findBySearchConditions(
                name = name,
                minPrice = minPrice,
                maxPrice = maxPrice,
                pageable = pageable
            )
        }
    }

    "이름으로 상품이 검색 되어야 한다" {
        // Given
        val name = "테스트"
        every { productService.findByName(name) } returns productList

        // When
        val result = displayProductUseCase.executeByName(name)

        // Then
        result shouldBe productList
        verify { productService.findByName(name) }
    }
})
