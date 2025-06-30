package me.bread.product.application.service

import me.bread.product.domain.entity.Product
import me.bread.product.domain.repository.ProductRepository
import me.bread.product.mapper.ProductMapper
import me.bread.product.presentation.support.error.ErrorType
import me.bread.product.presentation.support.error.ProductException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

/**
 * 상품 서비스
 * 상품 관련 기능을 제공하는 서비스
 */
@Component
class ProductService(
    private val productRepository: ProductRepository,
) {

    /**
     * 상품 조회
     * ID로 상품을 조회하는 메소드
     * @param id 상품 ID
     * @return 조회된 상품 또는 null
     */
    @Transactional
    fun findById(id: String) =
        productRepository.findById(id) ?: throw ProductException(
            ErrorType.PRODUCT_NOT_FOUND,
        )

    /**
     * 상품 저장
     * 상품을 저장하는 메소드
     * @param product 저장할 상품
     */
    @Transactional
    fun save(product: Product) =
        productRepository.save(product)

    /**
     * 페이징 및 검색 조건으로 상품 조회
     * 지정된 검색 조건과 페이징 정보로 상품을 조회한다
     *
     * @param name 상품 이름 (부분 일치)
     * @param minPrice 최소 가격
     * @param maxPrice 최대 가격
     * @param pageable 페이징 정보
     * @return 페이징된 상품 목록
     */
    @Transactional(readOnly = true)
    fun findBySearchConditions(
        name: String? = null,
        minPrice: BigDecimal? = null,
        maxPrice: BigDecimal? = null,
        pageable: Pageable,
    ): Page<Product> {
        return productRepository.findBySearchConditions(
            name = name,
            minPrice = minPrice?.toDouble(),
            maxPrice = maxPrice?.toDouble(),
            pageable = pageable,
        )
    }

    /**
     * 상품 이름으로 상품 검색
     * 상품 이름에 지정된 문자열이 포함된 상품을 검색한다
     *
     * @param name 검색할 상품 이름
     * @return 검색된 상품 목록
     */
    @Transactional(readOnly = true)
    fun findByName(name: String): List<Product> {
        return productRepository.findByNameContainingIgnoreCase(name)
            .map { ProductMapper.toDomain(it) }
    }
}
