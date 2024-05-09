package me.bread.order.infrastructure.r2dbc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("order_item")
class OrderItemEntity(
    @Id @Column("id") val id: Long? = null,
    @Column("orderId") val orderId: Long,
    @Column("productItemId") val productItemId: Long,
    @Column("productName") val productName: String,
    @Column("productPrice") val productPrice: BigDecimal,
    @Column("quantity") val quantity: Long,
) {
    override fun toString(): String {
        return "OrderItemEntity(" +
            "id=$id, " +
            "productItemId=$productItemId," +
            " productName='$productName', " +
            "productPrice=$productPrice, " +
            "quantity=$quantity)"
    }
}
