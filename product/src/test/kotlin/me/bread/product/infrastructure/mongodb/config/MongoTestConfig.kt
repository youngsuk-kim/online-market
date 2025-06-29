package me.bread.product.infrastructure.mongodb.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients

@TestConfiguration
@EnableMongoRepositories(basePackages = ["me.bread.product.infrastructure.mongodb.repository"])
class MongoTestConfig {

    companion object {
        val mongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:4.4.6"))
            .withExposedPorts(27017)

        init {
            mongoDBContainer!!.start()
        }
    }

    @Bean
    fun mongoClient(): MongoClient {
        val connectionString = mongoDBContainer!!.replicaSetUrl
        return MongoClients.create(connectionString)
    }

    @Bean
    fun mongoTemplate(mongoClient: MongoClient): MongoTemplate {
        return MongoTemplate(mongoClient, "test")
    }
}
