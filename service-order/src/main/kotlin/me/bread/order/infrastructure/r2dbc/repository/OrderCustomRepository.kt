package me.bread.order.infrastructure.r2dbc.repository

import me.bread.order.domain.enums.OrderStatus
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.stereotype.Repository

@Repository
class OrderCustomRepository(
    private val databaseClient: DatabaseClient,
) {

    suspend fun updateStatus(id: Long, status: OrderStatus) = databaseClient
        .sql("UPDATE orders set status = :status WHERE id = :id")
        .bind("id", id)
        .bind("status", status.name)
        .await()
}
