package me.bread.product.application.service

import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.stereotype.Component
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition

/**
 * 커스텀 트랜잭션 관리자
 * 스프링의 @Transactional 어노테이션 대신 사용하는 클래스
 */
@Component
class CustomTransactionManager(
    private val transactionManager: JpaTransactionManager
) {
    /**
     * 트랜잭션 시작
     * 새로운 트랜잭션을 시작하고 트랜잭션 상태를 반환
     */
    fun begin(): TransactionStatus {
        return transactionManager.getTransaction(DefaultTransactionDefinition())
    }

    /**
     * 트랜잭션 커밋
     * 현재 트랜잭션을 커밋
     */
    fun commit(status: TransactionStatus) {
        transactionManager.commit(status)
    }

    /**
     * 트랜잭션 롤백
     * 현재 트랜잭션을 롤백
     */
    fun rollback(status: TransactionStatus) {
        transactionManager.rollback(status)
    }

    /**
     * 트랜잭션 실행
     * 주어진 함수를 트랜잭션 내에서 실행하고 결과를 반환
     */
    fun <T> executeInTransaction(action: () -> T): T {
        val status = begin()
        return try {
            val result = action()
            commit(status)
            result
        } catch (e: Exception) {
            rollback(status)
            throw e
        }
    }
}
