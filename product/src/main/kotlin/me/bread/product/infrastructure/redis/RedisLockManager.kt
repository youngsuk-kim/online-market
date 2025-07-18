package me.bread.product.infrastructure.redis

import me.bread.product.application.annotation.Live
import me.bread.product.application.service.manager.LockManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.integration.redis.util.RedisLockRegistry
import org.springframework.stereotype.Component
import java.util.concurrent.locks.Lock

@Live
@Component
class RedisLockManager(@Autowired private val connectionFactory: RedisConnectionFactory) :
    LockManager {

    private val lockRegistry = RedisLockRegistry(connectionFactory, "lockRegistry")

    override fun getLock(): Lock {
        return lockRegistry.obtain("stock")
    }

    override fun unlock(lock: Lock) {
        lock.unlock()
    }
}
