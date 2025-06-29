package me.bread.product.infrastructure.jpa.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration
class MySQLTestConfig {

    companion object {
        @ServiceConnection
        val mysqlContainer = MySQLContainer(DockerImageName.parse("mysql:8.0"))
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true)

        init {
            mysqlContainer.start()
        }
    }
}
