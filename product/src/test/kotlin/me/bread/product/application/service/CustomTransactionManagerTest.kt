package me.bread.product.application.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.bread.product.application.service.manager.CustomTransactionManager
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.TransactionStatus

class CustomTransactionManagerTest : StringSpec({
    // 목 의존성
    val jpaTransactionManager = mockk<JpaTransactionManager>()
    val transactionStatus = mockk<TransactionStatus>()

    // 테스트할 서비스 생성
    val customTransactionManager = CustomTransactionManager(jpaTransactionManager)

    // 예외 객체
    val exception = RuntimeException("테스트 예외")

    "트랜잭션에서 함수가 성공적으로 실행될 때, 결과를 반환하고 트랜잭션을 커밋해야 한다" {
        // Given
        every { jpaTransactionManager.getTransaction(any()) } returns transactionStatus
        every { jpaTransactionManager.commit(transactionStatus) } returns Unit

        // When
        val result = customTransactionManager.executeInTransaction {
            "성공"
        }

        // Then
        result shouldBe "성공"
        verify { jpaTransactionManager.commit(transactionStatus) }
    }

    "트랜잭션에서 예외를 발생시키는 함수를 실행할 때, 트랜잭션을 롤백하고 예외를 다시 던져야 한다" {
        // Given
        every { jpaTransactionManager.getTransaction(any()) } returns transactionStatus
        every { jpaTransactionManager.rollback(transactionStatus) } returns Unit

        // When & Then
        val thrownException = shouldThrow<RuntimeException> {
            customTransactionManager.executeInTransaction {
                throw exception
            }
        }

        // Then
        thrownException shouldBeSameInstanceAs exception
        verify { jpaTransactionManager.rollback(transactionStatus) }
    }

    "트랜잭션을 시작할 때, 트랜잭션 상태를 반환해야 한다" {
        // Given
        every { jpaTransactionManager.getTransaction(any()) } returns transactionStatus

        // When
        val result = customTransactionManager.begin()

        // Then
        result shouldBeSameInstanceAs transactionStatus
    }

    "트랜잭션을 커밋할 때, 트랜잭션을 커밋해야 한다" {
        // Given
        every { jpaTransactionManager.commit(transactionStatus) } returns Unit

        // When
        customTransactionManager.commit(transactionStatus)

        // Then
        verify { jpaTransactionManager.commit(transactionStatus) }
    }

    "트랜잭션을 롤백할 때, 트랜잭션을 롤백해야 한다" {
        // Given
        every { jpaTransactionManager.rollback(transactionStatus) } returns Unit

        // When
        customTransactionManager.rollback(transactionStatus)

        // Then
        verify { jpaTransactionManager.rollback(transactionStatus) }
    }
})
