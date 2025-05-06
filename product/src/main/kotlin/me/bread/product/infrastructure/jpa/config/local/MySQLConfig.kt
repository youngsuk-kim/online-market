package me.bread.product.infrastructure.jpa.config.local

import me.bread.product.application.annotation.LocalDev
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@LocalDev
@Configuration
class MySQLConfig {

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver")
        dataSource.url = "jdbc:mysql://localhost:3307/product"
        dataSource.username = "root"
        dataSource.password = "secret1!"
        return dataSource
    }
}
