package me.bread.product.application.service

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.bread.product.domain.entity.Product
import me.bread.product.domain.repository.ProductRepository
import java.math.BigDecimal

class ProductServiceTest : StringSpec({
    // 의존성 목 객체
    val productRepository = mockk<ProductRepository>()
    val transactionManager = mockk<CustomTransactionManager>()

    // 테스트할 서비스 생성
    val productService = ProductService(productRepository, transactionManager)

    // 테스트 데이터
    val productId = 1L
    val product = Product(id = productId, name = "테스트 상품", price = BigDecimal("100.00"))

    "상품이 저장소에 존재하는 경우, ID로 상품을 찾을 때 상품을 반환해야 한다" {
        // 테스트를 위한 목 설정
        every { transactionManager.executeInTransaction<Product?>(any()) } answers {
            firstArg<() -> Product?>().invoke()
        }
        every { productRepository.findById(productId) } returns product

        // 실행
        val result = productService.findById(productId)

        // 검증
        result shouldBeSameInstanceAs product
        verify { productRepository.findById(productId) }
    }

    "상품이 저장소에 존재하지 않는 경우, ID로 상품을 찾을 때 null을 반환해야 한다" {
        // 테스트를 위한 목 설정
        every { transactionManager.executeInTransaction<Product?>(any()) } answers {
            firstArg<() -> Product?>().invoke()
        }
        every { productRepository.findById(productId) } returns null

        // 실행
        val result = productService.findById(productId)

        // 검증
        result shouldBe null
        verify { productRepository.findById(productId) }
    }

    "저장할 상품이 주어진 경우, 상품을 저장할 때 상품을 저장소에 저장해야 한다" {
        // 테스트를 위한 목 설정
        every { transactionManager.executeInTransaction<Unit>(any()) } answers {
            firstArg<() -> Unit>().invoke()
        }
        every { productRepository.save(product) } returns Unit

        // 실행
        productService.save(product)

        // 검증
        verify { productRepository.save(product) }
    }
})
