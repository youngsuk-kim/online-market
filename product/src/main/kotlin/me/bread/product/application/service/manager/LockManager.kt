package me.bread.product.application.service.manager

import me.bread.product.presentation.support.error.ErrorType
import me.bread.product.presentation.support.error.ProductException
import java.util.concurrent.locks.Lock

/**
 * 락 관리자 인터페이스
 * 동시성 제어를 위한 락 관리 기능을 제공하는 인터페이스
 */
interface LockManager {
    /**
     * 락 획득
     * 새로운 락 객체를 반환한다
     * @return 락 객체
     */
    fun getLock(): Lock

    /**
     * 락 해제
     * 지정된 락을 해제한다
     * @param lock 해제할 락 객체
     */
    fun unlock(lock: Lock)

    /**
     * 락 사용 유틸리티 함수
     * 락을 획득하고 작업을 수행한 후 자동으로 락을 해제한다
     * @param block 락을 획득한 상태에서 실행할 코드 블록
     * @throws ProductException 락 획득에 실패한 경우 발생
     */
    fun Lock.use(block: () -> Unit) {
        try {
            if (!this.tryLock()) throw ProductException(
                ErrorType.UNABLE_TO_ACQUIRE_LOCK,
                ErrorType.UNABLE_TO_ACQUIRE_LOCK.message,
            )

            block()
        } finally {
            unlock()
        }
    }

}
