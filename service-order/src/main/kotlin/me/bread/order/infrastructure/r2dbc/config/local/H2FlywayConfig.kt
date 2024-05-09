package me.bread.order.infrastructure.r2dbc.config.local

import me.bread.order.application.annotation.Local
import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource

@Local
@Configuration
class H2FlywayConfig {

    @Bean
    fun flywayH2(): Flyway {
        // Configure JDBC DataSource for Flyway
        val dataSource = DriverManagerDataSource().apply {
            setDriverClassName("org.h2.Driver")
            url = "jdbc:h2:mem:order;DB_CLOSE_DELAY=-1;MODE=MYSQL"
            username = "sa"
            password = ""
        }

        // Create Flyway instance with the DataSource and execute migrations
        val flyway = Flyway.configure().dataSource(dataSource).load()
        flyway.migrate()

        return flyway
    }
}
