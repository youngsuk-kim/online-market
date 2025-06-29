package me.bread.product.presentation.rest

import me.bread.product.application.usecase.DisplayProductUseCase
import me.bread.product.application.usecase.StockManageUseCase
import me.bread.product.domain.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/products")
class ProductController(
    private val stockManageUseCase: StockManageUseCase,
    private val displayProductUseCase: DisplayProductUseCase
) {

    /**
     * 상품 재고 감소
     */
    @PostMapping("/{productId}/items/{itemId}")
    fun decrease(@PathVariable("productId") productId: Long, @PathVariable("itemId") itemId: Long) {
        stockManageUseCase.execute(productId, itemId)
    }

    /**
     * 상품 조회
     */
    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long): Product {
        return displayProductUseCase.execute(id)
    }

    /**
     * 페이징 및 검색 조건으로 상품 조회
     */
    @GetMapping("/search")
    fun searchProducts(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) minPrice: BigDecimal?,
        @RequestParam(required = false) maxPrice: BigDecimal?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sortBy: String,
        @RequestParam(defaultValue = "ASC") sortDirection: String
    ): Page<Product> {
        val direction = if (sortDirection.equals("DESC", ignoreCase = true)) {
            Sort.Direction.DESC
        } else {
            Sort.Direction.ASC
        }

        val pageable = PageRequest.of(page, size, Sort.by(direction, sortBy))

        return displayProductUseCase.executeWithSearchConditions(
            name = name,
            minPrice = minPrice,
            maxPrice = maxPrice,
            pageable = pageable
        )
    }

    /**
     * 상품 이름으로 검색
     */
    @GetMapping("/search/name")
    fun searchProductsByName(@RequestParam name: String): List<Product> {
        return displayProductUseCase.executeByName(name)
    }
}
