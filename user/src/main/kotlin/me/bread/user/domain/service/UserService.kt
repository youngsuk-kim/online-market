package me.bread.user.domain.service

import me.bread.user.domain.entity.User
import org.jetbrains.exposed.sql.transactions.transaction

data class UserResult(
    val name: String,
    val age: Int,
)

class UserService {

    fun save(userResult: UserResult) {
        transaction {
            User.new {
                User.create(userResult.name, userResult.age)
            }
        }
    }
}
