package me.bread.product.infrastructure.jpa.repository

import me.bread.product.domain.enums.ProductOption
import me.bread.product.infrastructure.jpa.builder.ProductEntityBuilder
import me.bread.product.infrastructure.jpa.builder.ProductItemEntityBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class ProductItemJpaRepositoryTest {
    @Autowired
    private lateinit var productItemJpaRepository: ProductItemJpaRepository

    @Autowired
    private lateinit var productJpaRepository: ProductJpaRepository

    @Test
    fun `상품 아이템을 저장하고 ID로 조회할 수 있어야 한다`() {
        // Given
        val productEntity = ProductEntityBuilder.aProduct().build()
        productJpaRepository.save(productEntity)

        val productItemEntity = ProductItemEntityBuilder.anItem()
            .productEntity(productEntity)
            .build()

        // When
        productItemJpaRepository.save(productItemEntity)
        val foundItem = productItemJpaRepository.findById(productItemEntity.id).orElse(null)

        // Then
        assertEquals(productItemEntity, foundItem)
    }

    @Test
    fun `상품 ID로 해당 상품의 모든 아이템을 조회할 수 있어야 한다`() {
        // Given
        val productEntity = ProductEntityBuilder.aProduct()
            .name("테스트 상품")
            .price("10000.00")
            .build()
        productJpaRepository.save(productEntity)

        val productItemEntity1 = ProductItemEntityBuilder.anItem()
            .productEntity(productEntity)
            .optionKey(ProductOption.COLOR)
            .optionValue("빨간색")
            .stock(10)
            .build()

        val productItemEntity2 = ProductItemEntityBuilder.anItem()
            .productEntity(productEntity)
            .optionKey(ProductOption.SIZE)
            .optionValue("대형")
            .stock(5)
            .build()
        productItemJpaRepository.save(productItemEntity1)
        productItemJpaRepository.save(productItemEntity2)

        // When
        val items = productItemJpaRepository.findByProductEntityId(productEntity.id)

        // Then
        assertEquals(2, items.size)

        // 아이템 내용 검증
        val sortedItems = items.sortedBy { it.optionValue }
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
        val productEntity1 = ProductEntityBuilder.aProduct()
            .name("테스트 상품 1")
            .price("10000.00")
            .build()
        productJpaRepository.save(productEntity1)

        val productItemEntity1 = ProductItemEntityBuilder.anItem()
            .productEntity(productEntity1)
            .optionKey(ProductOption.COLOR)
            .optionValue("빨간색")
            .stock(10)
            .build()
        productItemJpaRepository.save(productItemEntity1)

        // 두 번째 상품
        val productEntity2 = ProductEntityBuilder.aProduct()
            .name("테스트 상품 2")
            .price("20000.00")
            .build()
        productJpaRepository.save(productEntity2)

        val productItemEntity2 = ProductItemEntityBuilder.anItem()
            .productEntity(productEntity2)
            .optionKey(ProductOption.COLOR)
            .optionValue("파란색")
            .stock(20)
            .build()

        val productItemEntity3 = ProductItemEntityBuilder.anItem()
            .productEntity(productEntity2)
            .optionKey(ProductOption.SIZE)
            .optionValue("중형")
            .stock(15)
            .build()

        productItemJpaRepository.save(productItemEntity2)
        productItemJpaRepository.save(productItemEntity3)

        // When
        // 첫 번째 상품의 아이템 조회
        val items1 = productItemJpaRepository.findByProductEntityId(productEntity1.id)

        // 두 번째 상품의 아이템 조회
        val items2 = productItemJpaRepository.findByProductEntityId(productEntity2.id)

        // Then
        // 첫 번째 상품 검증
        assertEquals(1, items1.size)
        assertEquals(ProductOption.COLOR, items1[0].optionKey)
        assertEquals("빨간색", items1[0].optionValue)
        assertEquals(10, items1[0].stock)

        // 두 번째 상품 검증
        assertEquals(2, items2.size)

        // 두 번째 상품의 아이템 내용 검증
        // 아이템을 옵션 키로 정렬하여 검증
        val itemsByKey = items2.associateBy { it.optionKey }

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
