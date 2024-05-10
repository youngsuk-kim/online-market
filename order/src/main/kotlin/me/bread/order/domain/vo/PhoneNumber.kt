package me.bread.order.domain.vo

data class PhoneNumber(val number: String) {
    init {
        require(isValidPhoneNumber(number)) {
            "Invalid phone number: $number"
        }
    }

    companion object {
        // 한국 휴대폰 번호 형식에 대한 정규식 패턴 (하이픈 없이)
        private val PHONE_NUMBER_PATTERN = Regex("^01[016789]\\d{7,8}$")

        fun isValidPhoneNumber(number: String): Boolean {
            return PHONE_NUMBER_PATTERN.matches(
                number,
            )
        }
    }

    override fun toString(): String = // 보기 좋게 하이픈을 추가하여 출력하고 싶은 경우 사용
        "${number.substring(
            0,
            3,
        )}-${number.substring(3, number.length - 4)}-${number.takeLast(4)}"
}
