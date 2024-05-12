package me.bread.product.application.service

import me.bread.order.application.annotation.Local
import me.bread.order.application.annotation.LocalDev
import org.springframework.stereotype.Component
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

@Local
@LocalDev
@Component
class LocalLockManager : LockManager {
    private val lock = ReentrantLock()

    override fun getLock(): Lock {
        return lock
    }

    override fun unlock(lock: Lock) {
        return lock.unlock()
    }
}
