package me.bread.product.infrastructure.jpa.repository

import me.bread.product.domain.entity.Product
import me.bread.product.domain.entity.ProductItem
import me.bread.product.domain.enums.ProductOption
import me.bread.product.domain.repository.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal

@ActiveProfiles("local")
@SpringBootTest
class ProductRepositoryAdapterTest {

    @Autowired
    private lateinit var productJpaRepository: ProductJpaRepository

    @Autowired
    private lateinit var productItemJpaRepository: ProductItemJpaRepository

    private lateinit var productRepository: ProductRepository

    @BeforeEach
    fun setUp() {
        productRepository = ProductRepositoryAdapter(productJpaRepository, productItemJpaRepository)
    }

    @Test
    fun `should save and retrieve product with product items`() {
        // Given
        val product = Product(
            id = 1L,
            name = "Test Product",
            price = BigDecimal(10000),
            productItems = mutableListOf(
                ProductItem(
                    id = 1L,
                    optionKey = ProductOption.SIZE,
                    optionValue = "Large",
                    stock = 10,
                ),
                ProductItem(
                    id = 2L,
                    optionKey = ProductOption.COLOR,
                    optionValue = "Red",
                    stock = 5,
                ),
            ),
        )

        // When
        productRepository.save(product)
        val retrievedProduct = productRepository.findById(product.id)

        // Then
        assertNotNull(retrievedProduct)
        assertEquals(product.id, retrievedProduct?.id)
        assertEquals(product.name, retrievedProduct?.name)
        assertEquals(product.price, retrievedProduct?.price)
        assertEquals(product.productItems.size, retrievedProduct?.productItems?.size)
    }
}
