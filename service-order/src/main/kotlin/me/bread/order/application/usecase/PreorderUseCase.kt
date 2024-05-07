package me.bread.order.application.usecase

import me.bread.order.application.service.AuthService
import me.bread.order.application.service.OrderService
import me.bread.order.application.service.ProductService
import me.bread.order.domain.entity.OrderItem
import org.springframework.stereotype.Component

@Component
class PreorderUseCase(
    private val authService: AuthService,
    private val orderService: OrderService,
    private val productService: ProductService,
) {
    fun execute(token: String, orderItems: List<OrderItem>) {
        // 고객 여부 확인
        authService.getCustomerId(token)

        // 상품 재고 확인
        productService.verifyStock(orderItems)

        // 총 비용 게산
        orderService.calculateTotalCharge()

        // 주문 아이템 생성
        orderService.preorder(orderItems)
    }
}
