package me.bread.product.application.service.manager

import me.bread.product.application.annotation.Local
import org.springframework.stereotype.Component
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * 로컬 락 관리자
 * 단일 JVM 환경에서 동작하는 락 관리자 구현체
 * 로컬 개발 및 테스트 환경에서 사용된다
 */
@Local
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
