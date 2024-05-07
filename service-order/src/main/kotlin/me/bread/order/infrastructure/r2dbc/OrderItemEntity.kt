package me.bread.order.infrastructure.r2dbc

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("order_item")
class OrderItemEntity(
    @Id val id: Long? = null,
    @Column val productItemId: Long,
    @Column val productPrice: BigDecimal,
    @Column val quantity: Long,
)
