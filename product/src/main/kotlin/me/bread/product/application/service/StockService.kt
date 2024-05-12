package me.bread.product.application.service

import me.bread.product.presentation.support.error.ErrorType
import me.bread.product.presentation.support.error.RestException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StockService(
    private val lockManager: LockManager,
    private val productService: ProductService,
) {

    @Transactional
    fun decrease(productId: Long, itemId: Long) {
        val lock = lockManager.getLock()

        if (lock.tryLock()) {
            // 락 획득
            try {
                productService.findById(productId)?.decreaseStock(itemId)
                    ?: throw RestException(ErrorType.INVALID_ARG_ERROR, "product item not found")
            } finally {
                lock.unlock()
            }
        } else {
            // 락 획득 실패
            throw RestException(ErrorType.INVALID_ARG_ERROR, "not enough stock to decrease")
        }
    }
}
