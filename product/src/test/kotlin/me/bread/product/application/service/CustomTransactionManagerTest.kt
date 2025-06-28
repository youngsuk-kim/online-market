package me.bread.product.application.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.TransactionStatus

class CustomTransactionManagerTest {
    // 목 의존성
    private val jpaTransactionManager = mockk<JpaTransactionManager>()
    private val transactionStatus = mockk<TransactionStatus>()

    // 테스트할 서비스 생성
    private lateinit var customTransactionManager: CustomTransactionManager

    // 예외 객체
    private val exception = RuntimeException("테스트 예외")

    @BeforeEach
    fun setup() {
        customTransactionManager = CustomTransactionManager(jpaTransactionManager)
    }

    @Test
    fun `트랜잭션에서 함수가 성공적으로 실행될 때, 결과를 반환하고 트랜잭션을 커밋해야 한다`() {
        // 테스트를 위한 목 설정
        every { jpaTransactionManager.getTransaction(any()) } returns transactionStatus
        every { jpaTransactionManager.commit(transactionStatus) } returns Unit

        // 실행
        val result = customTransactionManager.executeInTransaction {
            "성공"
        }

        // 검증
        assertEquals("성공", result)
        verify { jpaTransactionManager.commit(transactionStatus) }
    }

    @Test
    fun `트랜잭션에서 예외를 발생시키는 함수를 실행할 때, 트랜잭션을 롤백하고 예외를 다시 던져야 한다`() {
        // 테스트를 위한 목 설정
        every { jpaTransactionManager.getTransaction(any()) } returns transactionStatus
        every { jpaTransactionManager.rollback(transactionStatus) } returns Unit

        // 실행 및 검증
        val thrownException = assertThrows<RuntimeException> {
            customTransactionManager.executeInTransaction {
                throw exception
            }
        }

        assertSame(exception, thrownException)
        verify { jpaTransactionManager.rollback(transactionStatus) }
    }

    @Test
    fun `트랜잭션을 시작할 때, 트랜잭션 상태를 반환해야 한다`() {
        // 테스트를 위한 목 설정
        every { jpaTransactionManager.getTransaction(any()) } returns transactionStatus

        // 실행
        val result = customTransactionManager.begin()

        // 검증
        assertSame(transactionStatus, result)
    }

    @Test
    fun `트랜잭션을 커밋할 때, 트랜잭션을 커밋해야 한다`() {
        // 테스트를 위한 목 설정
        every { jpaTransactionManager.commit(transactionStatus) } returns Unit

        // 실행
        customTransactionManager.commit(transactionStatus)

        // 검증
        verify { jpaTransactionManager.commit(transactionStatus) }
    }

    @Test
    fun `트랜잭션을 롤백할 때, 트랜잭션을 롤백해야 한다`() {
        // 테스트를 위한 목 설정
        every { jpaTransactionManager.rollback(transactionStatus) } returns Unit

        // 실행
        customTransactionManager.rollback(transactionStatus)

        // 검증
        verify { jpaTransactionManager.rollback(transactionStatus) }
    }
}
