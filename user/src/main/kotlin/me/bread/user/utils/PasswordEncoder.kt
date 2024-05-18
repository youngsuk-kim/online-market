package me.bread.user.utils

object PasswordEncoder {
    fun hashPassword(password: String): String {
        return ""
//        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun verifyPassword(plainPassword: String, hashedPassword: String): Boolean {
        return true
//        return BCrypt.checkpw(plainPassword, hashedPassword)
    }
}
