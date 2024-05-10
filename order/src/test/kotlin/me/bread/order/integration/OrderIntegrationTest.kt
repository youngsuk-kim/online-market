package me.bread.order.integration

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import me.bread.order.BaseTestSetup
import me.bread.order.presentation.rest.request.PaymentRequest
import me.bread.order.presentation.rest.request.PreorderRequest
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class OrderIntegrationTest : BaseTestSetup() {

    @Test
    @DisplayName("사전 주문이 끝나고 결제 완료를 진행한다")
    fun `should complete preorder then process payment`() {
        // Step 1: Create a preorder
        val preorderToken = "test-token"
        val preorderItems = listOf(PreorderRequest.OrderItemRequest(1L, "nike", 10_000L, 1L))
        val preorderRequest = PreorderRequest(preorderToken, preorderItems)

        val orderId = given()
            .contentType(ContentType.JSON)
            .body(preorderRequest)
            .`when`()
            .post("/api/orders/pre")
            .then()
            .statusCode(200)
            .body("result", equalTo("SUCCESS"))
            .extract()
            .path<Int>("data") // Assuming the response contains an 'orderId'

        // Step 2: Make a payment using the orderId from the preorder
        val paymentKey = "payment123"
        val amount = 10_000L
        val paymentRequest = PaymentRequest(orderId.toLong(), paymentKey, amount)

        given()
            .contentType(ContentType.JSON)
            .body(paymentRequest)
            .`when`()
            .post("/api/payments/confirm")
            .then()
            .statusCode(200) // Assuming a successful payment returns HTTP 200
    }
}
