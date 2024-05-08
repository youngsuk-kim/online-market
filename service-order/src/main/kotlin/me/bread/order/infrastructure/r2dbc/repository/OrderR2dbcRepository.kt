package me.bread.order.infrastructure.r2dbc.repository

import me.bread.order.infrastructure.r2dbc.entity.OrderEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface OrderR2dbcRepository : CoroutineCrudRepository<OrderEntity, Long>
