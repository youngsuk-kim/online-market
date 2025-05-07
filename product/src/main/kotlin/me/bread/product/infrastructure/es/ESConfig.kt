package me.bread.product.infrastructure.es

import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import org.springframework.data.elasticsearch.support.HttpHeaders
import java.util.function.Supplier


@Configuration
class ESConfig : ElasticsearchConfiguration() {
    override fun clientConfiguration(): ClientConfiguration {
        return ClientConfiguration.builder()
            .connectedTo("localhost:9200")
            .withHeaders{
                HttpHeaders().apply {
                    this.add("Authorization", "ApiKey VjBLVXBKWUJXalk2bmN2NUlaQzA6eXhDTWlMTTJKcW95MmU3S3dMTmpRZw==")
                }
            }
            .build()
    }
}

