package me.bread.product.application.service

import me.bread.product.domain.entity.Product
import me.bread.product.domain.repository.ProductRepository
import org.springframework.stereotype.Component

/**
 * 상품 서비스
 * 상품 관련 기능을 제공하는 서비스
 */
@Component
class ProductService(
    private val productRepository: ProductRepository,
    private val transactionManager: CustomTransactionManager,
) {

    /**
     * 상품 조회
     * ID로 상품을 조회하는 메소드
     * @param id 상품 ID
     * @return 조회된 상품 또는 null
     */
    fun findById(id: Long) = transactionManager.executeInTransaction {
        productRepository.findById(id)
    }

    /**
     * 상품 저장
     * 상품을 저장하는 메소드
     * @param product 저장할 상품
     */
    fun save(product: Product) {
        transactionManager.executeInTransaction {
            productRepository.save(product)
        }
    }
}
