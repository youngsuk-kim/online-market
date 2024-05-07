package me.bread.order.infrastructure.r2dbc

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("order")
class OrderEntity(
    @Id val id: Long? = null,
    @Column val status: String,
)
