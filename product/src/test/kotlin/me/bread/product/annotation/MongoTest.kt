package me.bread.product.annotation

import me.bread.product.infrastructure.mongodb.config.MongoTestConfig
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers

@DataMongoTest
@ActiveProfiles("test")
@Import(MongoTestConfig::class)
@Testcontainers
annotation class MongoTest {
}
