package me.bread.order.domain.common

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import me.bread.order.domain.vo.PhoneNumber

class PhoneNumberTest : StringSpec({
    "toString() 호출 시 하이픈이 붙는다" {
        // Given When
        val sut = PhoneNumber("01053092392").toString()

        // Then
        sut shouldBe "010-5309-2392"
    }

    "핸드폰 번호에는 하이픈이 들어가면 안된다" {
        // Given When Then
        shouldThrow<IllegalArgumentException> {
            PhoneNumber("010-5309-2392")
        }
    }

    "핸드폰 번호 등록 성공" {
        // Given When
        val sut = PhoneNumber("01053092392")

        // Then
        sut shouldBe PhoneNumber("01053092392")
    }
})
