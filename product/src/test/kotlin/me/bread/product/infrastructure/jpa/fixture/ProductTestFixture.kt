package me.bread.product.infrastructure.jpa.fixture

import me.bread.product.domain.entity.Product
import me.bread.product.domain.entity.ProductItem
import me.bread.product.domain.enums.ProductOption
import me.bread.product.infrastructure.jpa.entity.ProductEntity
import me.bread.product.infrastructure.jpa.entity.ProductItemEntity
import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.MySQLContainer
import java.math.BigDecimal

/**
 * Test fixture class for product-related tests.
 * Provides common test data and container setup.
 */
class ProductTestFixture {
    companion object {
        fun createTestProductEntity(
            name: String = "테스트 상품",
            price: BigDecimal = BigDecimal("10000.00")
        ): ProductEntity {
            return ProductEntity(
                name = name,
                price = price
            )
        }

        fun createTestProductItemEntity(
            productEntity: ProductEntity,
            optionKey: ProductOption = ProductOption.COLOR,
            optionValue: String = "빨간색",
            stock: Int = 10
        ): ProductItemEntity {
            return ProductItemEntity(
                productEntity = productEntity,
                optionKey = optionKey,
                optionValue = optionValue,
                stock = stock
            )
        }

        fun createTestProduct(
            name: String = "테스트 상품",
            price: BigDecimal = BigDecimal("10000.00"),
            productItems: MutableSet<ProductItem> = mutableSetOf()
        ): Product {
            return Product(
                name = name,
                price = price,
                productItems = productItems
            )
        }

        fun createTestProductItem(
            optionKey: ProductOption = ProductOption.COLOR,
            optionValue: String = "빨간색",
            stock: Int = 10
        ): ProductItem {
            return ProductItem(
                optionKey = optionKey,
                optionValue = optionValue,
                stock = stock
            )
        }
    }
}
