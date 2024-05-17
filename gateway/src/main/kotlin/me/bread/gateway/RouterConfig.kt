package me.bread.gateway

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouterConfig(
    @Value("\${spring.cloud.gateway.routes[0].uri}")
    private val customerServiceUrl: String,

    @Value("\${spring.cloud.gateway.routes[1].uri}")
    private val orderServiceUrl: String,

    @Value("\${spring.cloud.gateway.routes[2].uri}")
    private val productServiceUrl: String,
) {

    @Bean
    fun myRoutes(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route { p ->
                p.path("/api/customers/**")
                    .uri(customerServiceUrl)
            }
            .route { p ->
                p.path("/api/orders/**")
                    .uri(orderServiceUrl)
            }
            .route { p ->
                p.path("/api/products/**")
                    .uri(productServiceUrl)
            }
            .build()
    }
}
