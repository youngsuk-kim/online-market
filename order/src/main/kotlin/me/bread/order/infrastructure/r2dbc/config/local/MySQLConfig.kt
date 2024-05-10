package me.bread.order.infrastructure.r2dbc.config.local

import io.asyncer.r2dbc.mysql.client.Client.logger
import io.r2dbc.proxy.ProxyConnectionFactory
import io.r2dbc.proxy.core.QueryExecutionInfo
import io.r2dbc.proxy.support.QueryExecutionInfoFormatter
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.Option
import me.bread.order.application.annotation.LocalDev
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.ReactiveTransactionManager

@LocalDev
@Configuration
@EnableR2dbcRepositories
class MySQLConfig : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val connectionFactory = ConnectionFactories.get(
            ConnectionFactoryOptions.builder().option(ConnectionFactoryOptions.DRIVER, "mysql")
                .option(ConnectionFactoryOptions.HOST, "localhost")
                .option(ConnectionFactoryOptions.PORT, 3306)
                .option(ConnectionFactoryOptions.USER, "root")
                .option(ConnectionFactoryOptions.PASSWORD, "secret1!")
                .option(ConnectionFactoryOptions.DATABASE, "order")
                .option(Option.valueOf("ssl"), false) // Disable SSL if not needed
                .build(),
        )

        val custom = QueryExecutionInfoFormatter()
            .addConsumer { info: QueryExecutionInfo, sb: StringBuilder ->
                // custom conversion
                sb.append("ConnID=")
                sb.append(info.connectionInfo.connectionId)
            }
            .newLine()
            .showQuery()
            .newLine()
            .showBindings()
            .newLine()

        val proxyConnection = ProxyConnectionFactory.builder(connectionFactory)
            .onAfterQuery { queryInfo: QueryExecutionInfo -> // listener
                logger.info(custom.format(queryInfo))
            }
            .build()

        return proxyConnection
    }

    @Bean
    fun transactionManager(connectionFactory: ConnectionFactory): ReactiveTransactionManager {
        return R2dbcTransactionManager(connectionFactory)
    }
}
