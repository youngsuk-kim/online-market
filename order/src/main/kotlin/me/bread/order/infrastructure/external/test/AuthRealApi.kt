package me.bread.order.infrastructure.external.test

import me.bread.order.application.annotation.Live
import me.bread.order.application.external.AuthApi
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Live
@Component
class AuthRealApi(
    private val webClient: WebClient,
) : AuthApi {
    override suspend fun fetchCustomerIdBy(token: String): Long = webClient.post()
        .uri("/api/customers")
        .bodyValue(token)
        .retrieve()
        .awaitBody<Long>()
}
