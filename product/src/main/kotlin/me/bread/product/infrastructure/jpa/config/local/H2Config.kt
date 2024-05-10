package me.bread.product.infrastructure.jpa.config.local

import me.bread.order.application.annotation.Local
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Local
@Configuration
class H2Config {

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.h2.Driver")
        dataSource.url = "jdbc:h2:mem:product;DB_CLOSE_DELAY=-1"
        dataSource.username = "sa"
        dataSource.password = ""
        return dataSource
    }
}
