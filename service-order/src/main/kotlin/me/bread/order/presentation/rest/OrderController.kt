package me.bread.order.presentation.rest

import me.bread.order.application.usecase.PreorderUseCase
import me.bread.order.presentation.rest.request.PreorderRequest
import me.bread.order.presentation.support.response.RestResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val preorderUseCase: PreorderUseCase,
) {

    @PostMapping("/api/orders/pre")
    fun preorder(@RequestBody request: PreorderRequest): RestResponse<Any> {
        preorderUseCase.execute(
            token = request.token,
            orderItems = request.orderItems.map { it.toOrderItem() },
        )

        return RestResponse.success()
    }
}
