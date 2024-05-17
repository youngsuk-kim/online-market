package me.bread.user.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable() {
    val name = varchar("name", 50)
    val age = integer("age")
}
