package me.bread.user.service

import me.bread.user.entity.User
import org.jetbrains.exposed.sql.transactions.transaction

data class UserResult(
    val email: String,
    val password: String,
)

object UserService {

    fun save(userResult: UserResult) = transaction {
        User.create(userResult.email, userResult.password)
    }

    fun findByEmail(email: String) = transaction {
        User.findByEmail(email)
    }
}
