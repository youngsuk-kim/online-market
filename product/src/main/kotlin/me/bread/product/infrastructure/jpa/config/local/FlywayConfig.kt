package me.bread.product.infrastructure.jpa.config.local

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class FlywayConfig(private val dataSource: DataSource) {

    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        return Flyway.configure()
            .cleanDisabled(false)
            .dataSource(dataSource)
            .locations("db/migration")
            .load()
    }

    @Bean
    fun cleanDatabase(flyway: Flyway): Flyway {
//        flyway.clean()
        flyway.migrate()

        return flyway
    }
}
