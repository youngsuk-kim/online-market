package me.bread.product.application.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.bread.product.domain.entity.Inventory
import me.bread.product.domain.repository.InventoryRepository
import me.bread.product.presentation.support.error.RestException

class InventoryServiceTest : StringSpec({
    // 목 의존성
    val inventoryRepository = mockk<InventoryRepository>()

    // 테스트할 서비스 생성
    val inventoryService = InventoryService(inventoryRepository)

    // 테스트 데이터
    val inventoryId = 1L
    val productItemId = 2L
    val initialStock = 10

    "재고를 ID로 조회할 수 있어야 한다" {
        // Given
        val inventory = Inventory(inventoryId, productItemId, initialStock)
        every { inventoryRepository.findById(inventoryId) } returns inventory

        // When
        val result = inventoryService.findById(inventoryId)

        // Then
        result shouldBe inventory
        verify { inventoryRepository.findById(inventoryId) }
    }

    "상품 아이템 ID로 재고를 조회할 수 있어야 한다" {
        // Given
        val inventory = Inventory(inventoryId, productItemId, initialStock)
        every { inventoryRepository.findByProductItemId(productItemId) } returns inventory

        // When
        val result = inventoryService.findByProductItemId(productItemId)

        // Then
        result shouldBe inventory
        verify { inventoryRepository.findByProductItemId(productItemId) }
    }

    "재고를 감소시킬 수 있어야 한다" {
        // Given
        val inventory = Inventory(inventoryId, productItemId, initialStock)
        val updatedInventory = Inventory(inventoryId, productItemId, initialStock - 1)

        every { inventoryRepository.findByProductItemId(productItemId) } returns inventory
        every { inventoryRepository.save(any()) } returns updatedInventory

        // When
        val result = inventoryService.decrease(productItemId)

        // Then
        result.stock shouldBe initialStock - 1
        verify { inventoryRepository.findByProductItemId(productItemId) }
        verify { inventoryRepository.save(any()) }
    }

    "재고가 0인 경우 감소시키면 예외가 발생해야 한다" {
        // Given
        val inventory = Inventory(inventoryId, productItemId, 0)
        every { inventoryRepository.findByProductItemId(productItemId) } returns inventory

        // When & Then
        shouldThrow<IllegalStateException> {
            inventoryService.decrease(productItemId)
        }

        verify { inventoryRepository.findByProductItemId(productItemId) }
    }

    "재고를 증가시킬 수 있어야 한다" {
        // Given
        val inventory = Inventory(inventoryId, productItemId, initialStock)
        val increaseQuantity = 5
        val updatedInventory = Inventory(inventoryId, productItemId, initialStock + increaseQuantity)

        every { inventoryRepository.findByProductItemId(productItemId) } returns inventory
        every { inventoryRepository.save(any()) } returns updatedInventory

        // When
        val result = inventoryService.increase(productItemId, increaseQuantity)

        // Then
        result.stock shouldBe initialStock + increaseQuantity
        verify { inventoryRepository.findByProductItemId(productItemId) }
        verify { inventoryRepository.save(any()) }
    }
})
