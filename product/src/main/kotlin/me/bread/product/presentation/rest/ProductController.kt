package me.bread.product.presentation.rest

import me.bread.product.application.usecase.StockManageUseCase
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(private val stockManageUseCase: StockManageUseCase) {

    @PostMapping("/products/{productId}/items/{itemId}")
    fun decrease(@PathVariable("productId") productId: Long, @PathVariable("itemId") itemId: Long) {
        stockManageUseCase.execute(productId, itemId)
    }
}
