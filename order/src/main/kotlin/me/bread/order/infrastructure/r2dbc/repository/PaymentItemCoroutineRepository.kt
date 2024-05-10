package me.bread.order.infrastructure.r2dbc.repository

import me.bread.order.infrastructure.r2dbc.entity.PaymentItemEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PaymentItemCoroutineRepository : CoroutineCrudRepository<PaymentItemEntity, Long>
