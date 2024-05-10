package me.bread.product.infrastructure.jpa.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.Properties
import javax.sql.DataSource

@Configuration
class JPAConfig(private val dataSource: DataSource) {

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource
        em.setPackagesToScan("me.bread.product.infrastructure.jpa.entity")
        val vendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        em.setJpaProperties(additionalProperties())
        return em
    }

    @Bean
    fun transactionManager(): JpaTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory().`object`
        return transactionManager
    }

    fun additionalProperties(): Properties {
        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", "none")
        properties.setProperty("hibernate.show_sql", "true")
        properties.setProperty("hibernate.format_sql", "true")
        return properties
    }
}
