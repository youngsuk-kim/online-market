package me.bread.order.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("orders_item")
class OrderItem(
    @Column("id") @Id val id: Long = -1,
    @Column("orderId") val orderId: Long = -1,
    @Column("productItemId") val productItemId: Long,
    @Column("productName") val productName: String,
    @Column("productPrice") val productPrice: BigDecimal,
    @Column("quantity") val quantity: Long,
)
