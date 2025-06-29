package me.bread.product.infrastructure.jpa.repository

import me.bread.product.domain.enums.ProductOption
import me.bread.product.infrastructure.jpa.entity.ProductEntity
import me.bread.product.infrastructure.jpa.entity.ProductItemEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ProductItemJpaRepositoryTest {
    @Autowired
    private lateinit var productItemJpaRepository: ProductItemJpaRepository

    @Autowired
    private lateinit var productJpaRepository: ProductJpaRepository

    @Test
    fun `상품 아이템을 저장하고 ID로 조회할 수 있어야 한다`() {
        // Given
        val productEntity = ProductEntity(
            name = "테스트 상품",
            price = BigDecimal("10000.00")
        )
        productJpaRepository.save(productEntity)

        val productItemEntity = ProductItemEntity(
            productEntity = productEntity,
            optionKey = ProductOption.COLOR,
            optionValue = "빨간색",
            stock = 10
        )

        // When
        productItemJpaRepository.save(productItemEntity)
        val foundItem = productItemJpaRepository.findById(productItemEntity.id).orElse(null)

        // Then
        assertEquals(productItemEntity, foundItem)
    }

    @Test
    fun `상품 ID로 해당 상품의 모든 아이템을 조회할 수 있어야 한다`() {
        // Given
        val productEntity = ProductEntity(
            name = "테스트 상품",
            price = BigDecimal("10000.00")
        )
        productJpaRepository.save(productEntity)

        val productItemEntity1 = ProductItemEntity(
            productEntity = productEntity,
            optionKey = ProductOption.COLOR,
            optionValue = "빨간색",
            stock = 10
        )

        val productItemEntity2 = ProductItemEntity(
            productEntity = productEntity,
            optionKey = ProductOption.SIZE,
            optionValue = "대형",
            stock = 5
        )
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
        val productEntity1 = ProductEntity(
            name = "테스트 상품 1",
            price = BigDecimal("10000.00")
        )
        productJpaRepository.save(productEntity1)

        val productItemEntity1 = ProductItemEntity(
            productEntity = productEntity1,
            optionKey = ProductOption.COLOR,
            optionValue = "빨간색",
            stock = 10
        )
        productItemJpaRepository.save(productItemEntity1)

        // 두 번째 상품
        val productEntity2 = ProductEntity(
            name = "테스트 상품 2",
            price = BigDecimal("20000.00")
        )
        productJpaRepository.save(productEntity2)

        val productItemEntity2 = ProductItemEntity(
            productEntity = productEntity2,
            optionKey = ProductOption.COLOR,
            optionValue = "파란색",
            stock = 20
        )

        val productItemEntity3 = ProductItemEntity(
            productEntity = productEntity2,
            optionKey = ProductOption.SIZE,
            optionValue = "중형",
            stock = 15
        )

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

    companion object {
        @Container
        private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.0").apply {
            withDatabaseName("testdb")
            withUsername("testuser")
            withPassword("testpass")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { mysqlContainer.jdbcUrl }
            registry.add("spring.datasource.username") { mysqlContainer.username }
            registry.add("spring.datasource.password") { mysqlContainer.password }
            registry.add("spring.jpa.hibernate.ddl-auto") { "create-drop" }
        }
    }
}
