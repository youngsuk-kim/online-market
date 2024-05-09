package me.bread.order.application.service

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
    suspend fun preorder(orderItem: List<OrderItem>): Long {
        val order = Order.preorder(orderItems = orderItem)

        val orderId = orderRepository.save(OrderMapper.toEntity(order))

        order.orderItems.forEach {
            orderItemRepository.save(OrderItemMapper.toEntity(it, orderId))
        }

        return orderId
    }

    @Transactional
    suspend fun paid(orderId: Long) {
        val order = findOrderBy(orderId)
        orderRepository.save(OrderMapper.toEntity(order))
    }

    @Transactional(readOnly = true)
    suspend fun validatePay(orderId: Long, amount: Long) {
        val order = findOrderBy(orderId)
        order.validatePayment(orderId, amount)
    }

    @Transactional(readOnly = true)
    suspend fun findOrderBy(orderId: Long): Order {
        val orderItems = orderItemRepository.findByOrderId(orderId)
        return orderRepository.findById(orderId, orderItems)
    }
}
