package me.bread.order.presentation.rest

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import me.bread.order.BaseTestSetup
import me.bread.order.presentation.rest.request.PreorderRequest
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class OrderControllerTest : BaseTestSetup() {

    @Test
    @DisplayName("사전 주문 API 호출 시, 주문 데이터가 저장 되어야 한다")
    fun `should create new order`() {
        val token = "test-token"
        val items = listOf(PreorderRequest.OrderItemRequest(1L, "nike", "10000", 1L))
        val request = PreorderRequest(token, items)

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .`when`()
            .post("/api/orders/pre")
            .then()
            .statusCode(200)
            .body("result", equalTo("SUCCESS"))
    }
}
