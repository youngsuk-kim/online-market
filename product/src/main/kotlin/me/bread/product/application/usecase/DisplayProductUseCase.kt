package me.bread.product.application.usecase

import me.bread.product.application.service.ProductService
import me.bread.product.domain.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.math.BigDecimal

/**
 * 상품 표시 유스케이스
 * 사용자에게 상품 정보를 표시하는 기능을 담당하는 유스케이스
 */
@Component
class DisplayProductUseCase(
    private val productService: ProductService
) {
    /**
     * 상품 조회 실행
     * 지정된 ID의 상품을 조회한다
     *
     * @param id 조회할 상품의 ID
     * @return 조회된 상품
     */
    fun execute(id: String): Product {
        return productService.findById(id)
    }

    /**
     * 페이징 및 검색 조건으로 상품 조회 실행
     * 지정된 검색 조건과 페이징 정보로 상품을 조회한다
     *
     * @param name 상품 이름 (부분 일치)
     * @param minPrice 최소 가격
     * @param maxPrice 최대 가격
     * @param pageable 페이징 정보
     * @return 페이징된 상품 목록
     */
    fun executeWithSearchConditions(
        name: String? = null,
        minPrice: BigDecimal? = null,
        maxPrice: BigDecimal? = null,
        pageable: Pageable
    ): Page<Product> {
        return productService.findBySearchConditions(
            name = name,
            minPrice = minPrice,
            maxPrice = maxPrice,
            pageable = pageable
        )
    }

    /**
     * 상품 이름으로 상품 검색 실행
     * 상품 이름에 지정된 문자열이 포함된 상품을 검색한다
     *
     * @param name 검색할 상품 이름
     * @return 검색된 상품 목록
     */
    fun executeByName(name: String): List<Product> {
        return productService.findByName(name)
    }
}
