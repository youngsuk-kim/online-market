package me.bread.order.infrastructure.r2dbc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("orders")
class OrderEntity(
    @Id @Column("id") val id: Long? = null,
    @Column("status") var status: String,
)
