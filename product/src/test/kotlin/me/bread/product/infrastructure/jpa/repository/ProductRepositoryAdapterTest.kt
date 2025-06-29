package me.bread.product.infrastructure.jpa.repository

import me.bread.product.domain.entity.Product
import me.bread.product.domain.entity.ProductItem
import me.bread.product.domain.enums.ProductOption
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
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
@Import(ProductRepositoryAdapter::class)
class ProductRepositoryAdapterTest {
    @Autowired
    private lateinit var productRepositoryAdapter: ProductRepositoryAdapter

    @Autowired
    private lateinit var productJpaRepository: ProductJpaRepository

    @Autowired
    private lateinit var productItemJpaRepository: ProductItemJpaRepository

    @Test
    fun `상품을 저장하면 ID가 생성되어야 한다`() {
        // 테스트 데이터 준비
        val productName = "테스트 상품"
        val productPrice = BigDecimal("10000.00")

        val product = Product(
            name = productName,
            price = productPrice
        )

        // 실행
        productRepositoryAdapter.save(product)

        // 검증 - 저장된 엔티티를 조회하여 ID 확인
        val savedEntity = productJpaRepository.findAll()
            .firstOrNull { it.name == productName && it.price == productPrice }

        assertNotNull(savedEntity)
        assertNotEquals(0L, savedEntity!!.id)
    }

    @Test
    fun `상품과 상품 아이템을 함께 저장하면 모두 저장되어야 한다`() {
        // 테스트 데이터 준비
        val productName = "테스트 상품"
        val productPrice = BigDecimal("10000.00")
        val optionValue = "빨간색"

        val productItem = ProductItem(
            optionKey = ProductOption.COLOR,
            optionValue = optionValue,
            stock = 10
        )

        val product = Product(
            name = productName,
            price = productPrice,
            productItems = mutableListOf(productItem)
        )

        // 실행
        productRepositoryAdapter.save(product)

        // 검증 - 저장된 엔티티를 조회
        val savedEntity = productJpaRepository.findAll()
            .firstOrNull { it.name == productName && it.price == productPrice }

        assertNotNull(savedEntity)

        // 저장된 엔티티의 ID로 상품 조회
        val savedProduct = productRepositoryAdapter.findById(savedEntity!!.id)
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
        // 테스트 데이터 준비
        val productName = "테스트 상품"
        val productPrice = BigDecimal("10000.00")

        val productItem1 = ProductItem(
            optionKey = ProductOption.COLOR,
            optionValue = "빨간색",
            stock = 10
        )

        val productItem2 = ProductItem(
            optionKey = ProductOption.SIZE,
            optionValue = "대형",
            stock = 5
        )

        val product = Product(
            name = productName,
            price = productPrice,
            productItems = mutableListOf(productItem1, productItem2)
        )

        // 저장
        productRepositoryAdapter.save(product)

        // 저장된 엔티티를 조회
        val savedEntity = productJpaRepository.findAll()
            .firstOrNull { it.name == productName && it.price == productPrice }

        assertNotNull(savedEntity)

        // 실행 - 저장된 엔티티의 ID로 상품 조회
        val foundProduct = productRepositoryAdapter.findById(savedEntity!!.id)

        // 검증
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
        // 실행
        val foundProduct = productRepositoryAdapter.findById(9999L)

        // 검증
        assertNull(foundProduct)
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
