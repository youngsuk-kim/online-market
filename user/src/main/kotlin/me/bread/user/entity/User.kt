package me.bread.user.entity

import me.bread.user.database.tables.UserTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(UserTable) {
        fun create(email: String, age: String): User = User.new {
            this.email = email
            this.password = age
        }

        fun findByEmail(email: String): User? = User.find {
            UserTable.email eq email
        }.firstOrNull()
    }
    private var email by UserTable.email
    private var password by UserTable.password

    override fun toString(): String = "User(id=$id, name=$email, age=$password)"
}
