package me.bread.product.infrastructure.redis

import me.bread.product.application.service.LockManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.integration.redis.util.RedisLockRegistry
import org.springframework.stereotype.Service
import java.util.concurrent.locks.Lock

@Service
class RedisLockManager(@Autowired private val connectionFactory: RedisConnectionFactory) :
    LockManager {

    private val lockRegistry = RedisLockRegistry(connectionFactory, "lockRegistry")

    override fun getLock(lockId: String): Lock? {
        val lock = lockRegistry.obtain(lockId)
        return if (lock.tryLock()) lock else null
    }

    override fun unlock(lock: Lock) {
        lock.unlock()
    }
}
