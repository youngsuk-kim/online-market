package me.bread.user.service

import io.ktor.server.plugins.NotFoundException
import me.bread.user.entity.User
import me.bread.user.utils.PasswordEncoder.hashPassword
import org.jetbrains.exposed.sql.transactions.transaction

object UserService {

    fun save(userResult: UserResult) = transaction {
        User.create(userResult.email, hashPassword(userResult.password))
    }

    fun findByEmail(email: String) = transaction {
        User.findByEmail(email) ?: throw NotFoundException("User $email not found")
    }
}
