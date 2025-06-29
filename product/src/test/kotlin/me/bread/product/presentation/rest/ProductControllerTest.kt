package me.bread.product.presentation.rest

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.bread.product.application.usecase.DisplayProductUseCase
import me.bread.product.application.usecase.StockManageUseCase
import me.bread.product.domain.entity.Product
import me.bread.product.infrastructure.jpa.builder.ProductBuilder
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.math.BigDecimal

class ProductControllerTest : StringSpec({
    // 의존성 목 객체
    val stockManageUseCase = mockk<StockManageUseCase>()
    val displayProductUseCase = mockk<DisplayProductUseCase>()

    // 테스트 대상
    val productController = ProductController(stockManageUseCase, displayProductUseCase)

    // MockMvc 설정
    val mockMvc = MockMvcBuilders.standaloneSetup(productController).build()

    // 테스트 데이터
    val productId = 1L
    val itemId = 2L
    val product = ProductBuilder.aProduct()
        .id(productId)
        .name("테스트 상품")
        .price("100.00")
        .build()

    val productList = listOf(
        ProductBuilder.aProduct().id(1L).name("상품1").build(),
        ProductBuilder.aProduct().id(2L).name("상품2").build(),
        ProductBuilder.aProduct().id(3L).name("상품3").build()
    )

    "decrease 엔드포인트는 상품 아이템의 재고를 감소시켜야 한다" {
        // Given
        every { stockManageUseCase.execute(productId, itemId) } returns Unit

        // When & Then
        mockMvc.perform(post("/products/{productId}/items/{itemId}", productId, itemId))
            .andExpect(status().isOk)

        verify { stockManageUseCase.execute(productId, itemId) }
    }

    "getProduct 엔드포인트는 ID로 상품을 조회해야 한다" {
        // Given
        every { displayProductUseCase.execute(productId) } returns product

        // When & Then
        mockMvc.perform(get("/products/{id}", productId))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(productId))
            .andExpect(jsonPath("$.name").value("테스트 상품"))
            .andExpect(jsonPath("$.price").value(100.00))

        verify { displayProductUseCase.execute(productId) }
    }

    "searchProducts 엔드포인트는 검색 조건과 페이징으로 상품을 조회해야 한다" {
        // Given
        val name = "테스트"
        val minPrice = BigDecimal("10.00")
        val maxPrice = BigDecimal("100.00")
        val pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"))
        val page = PageImpl(productList, pageable, productList.size.toLong())

        every {
            displayProductUseCase.executeWithSearchConditions(
                name = name,
                minPrice = minPrice,
                maxPrice = maxPrice,
                pageable = any()
            )
        } returns page

        // When & Then
        mockMvc.perform(get("/products/search")
                .param("name", name)
                .param("minPrice", "10.00")
                .param("maxPrice", "100.00")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("sortDirection", "ASC"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content").isArray)
            .andExpect(jsonPath("$.content.length()").value(3))
            .andExpect(jsonPath("$.totalElements").value(3))

        verify {
            displayProductUseCase.executeWithSearchConditions(
                name = name,
                minPrice = minPrice,
                maxPrice = maxPrice,
                pageable = any()
            )
        }
    }

    "searchProductsByName 엔드포인트는 이름으로 상품을 검색해야 한다" {
        // Given
        val name = "테스트"
        every { displayProductUseCase.executeByName(name) } returns productList

        // When & Then
        mockMvc.perform(get("/products/search/name")
                .param("name", name))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$.length()").value(3))

        verify { displayProductUseCase.executeByName(name) }
    }
})
