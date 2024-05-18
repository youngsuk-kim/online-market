package me.bread.user.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable("users") {
    val email = varchar("email", 50)
    val password = varchar("password", 255)
}
