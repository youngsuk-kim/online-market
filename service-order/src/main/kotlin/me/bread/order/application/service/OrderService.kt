package me.bread.order.application.service

import me.bread.order.adapter.OrderR2dbcMapper
import me.bread.order.domain.entity.Order
import me.bread.order.domain.entity.OrderItem
import me.bread.order.domain.repository.OrderRepository
import org.springframework.stereotype.Component

@Component
class OrderService(
    private val orderRepository: OrderRepository,
) {

    suspend fun preorder(orderItem: List<OrderItem>) {
        val order = Order.preorder(orderItem)
        orderRepository.save(OrderR2dbcMapper.toEntity(order))
    }
}
