package me.bread.order.infrastructure.r2dbc.config

import me.bread.order.application.annotation.LocalDev
import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource

@LocalDev
@Configuration
class MySQLFlywayConfig {
    @Bean
    fun flywayMysql(): Flyway? {
        val dataSource = DriverManagerDataSource().apply {
            setDriverClassName("com.mysql.cj.jdbc.Driver")
            url = "jdbc:mysql://localhost:3306/order"
            username = "root"
            password = "secret1!"
        }

        val flyway = Flyway.configure().cleanDisabled(false).dataSource(dataSource).load()

        flyway.clean()
        flyway.migrate()

        return flyway
    }
}
