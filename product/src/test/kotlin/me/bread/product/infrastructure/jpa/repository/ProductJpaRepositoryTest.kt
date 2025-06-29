package me.bread.product.infrastructure.jpa.repository

import me.bread.product.infrastructure.jpa.builder.ProductEntityBuilder
import me.bread.product.infrastructure.jpa.entity.ProductEntity
import me.bread.product.infrastructure.jpa.fixture.ProductTestFixture
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

@DataJpaTest
@ActiveProfiles("test")
class ProductJpaRepositoryTest {
    @Autowired
    private lateinit var productJpaRepository: ProductJpaRepository

    @Test
    fun `상품을 저장하면 ID가 생성되어야 한다`() {
        // Given
        val productEntity = ProductEntityBuilder.aProduct()
            .name("테스트 상품")
            .price("10000.00")
            .build()

        // When
        productJpaRepository.save(productEntity)

        // Then
        assertNotEquals(0, productEntity.id)
    }

    @Test
    fun `저장된 상품을 ID로 조회할 수 있어야 한다`() {
        // Given
        val productEntity = ProductEntityBuilder.aProduct()
            .name("테스트 상품")
            .price("10000.00")
            .build()
        productJpaRepository.save(productEntity)

        // When
        val foundProduct = productJpaRepository.findById(productEntity.id).orElse(null)

        // Then
        assertNotNull(foundProduct)
        assertEquals(productEntity.id, foundProduct.id)
        assertEquals("테스트 상품", foundProduct.name)
        assertEquals(BigDecimal("10000.00"), foundProduct.price)
    }

    @Test
    fun `상품 정보를 업데이트할 수 있어야 한다`() {
        // Given
        val productEntity = ProductEntityBuilder.aProduct()
            .name("테스트 상품")
            .price("10000.00")
            .build()
        productJpaRepository.save(productEntity)

        // When
        productEntity.name = "업데이트된 상품"
        productEntity.price = BigDecimal("20000.00")
        productJpaRepository.save(productEntity)
        val updatedProduct = productJpaRepository.findById(productEntity.id).orElse(null)

        // Then
        assertNotNull(updatedProduct)
        assertEquals("업데이트된 상품", updatedProduct.name)
        assertEquals(BigDecimal("20000.00"), updatedProduct.price)
    }

    @Test
    fun `상품을 삭제할 수 있어야 한다`() {
        // Given
        val productEntity = ProductEntityBuilder.aProduct()
            .name("테스트 상품")
            .price("10000.00")
            .build()
        productJpaRepository.save(productEntity)

        // When
        productJpaRepository.delete(productEntity)

        // Then
        val foundProduct = productJpaRepository.findById(productEntity.id).orElse(null)
        assertNull(foundProduct)
    }

    @Test
    fun `존재하지 않는 ID로 상품을 조회하면 null을 반환해야 한다`() {
        // Given
        // No setup needed

        // When
        val foundProduct = productJpaRepository.findById(9999L).orElse(null)

        // Then
        assertNull(foundProduct)
    }
}
