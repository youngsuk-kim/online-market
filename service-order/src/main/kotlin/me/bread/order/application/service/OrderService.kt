package me.bread.order.application.service

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.bread.order.adapter.OrderItemMapper
import me.bread.order.adapter.OrderMapper
import me.bread.order.domain.entity.Order
import me.bread.order.domain.entity.OrderItem
import me.bread.order.domain.repository.OrderItemRepository
import me.bread.order.domain.repository.OrderRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
) {

    @Transactional
    suspend fun preorder(orderItem: List<OrderItem>) {
        val order = Order.preorder(orderItem)

        coroutineScope {
            launch {
                order.orderItems.forEach {
                    println(it.productItemId)
                    orderItemRepository.save(OrderItemMapper.toEntity(it))
                }
            }

            launch {
                orderRepository.save(OrderMapper.toEntity(order))
            }
        }.join()
    }
}
