package me.bread.order.infrastructure.r2dbc.entity

import me.bread.order.domain.enums.OrderStatus
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("orders")
class OrderEntity(
    @Id @Column("id") val id: Long = -1,
    @Column("status") var status: String,
) {
    fun changeStatus() {
        this.status = OrderStatus.PAYED.name
    }
}
