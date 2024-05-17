package me.bread.user.domain.entity

import me.bread.user.database.tables.UserTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(UserTable) {
        fun create(name: String, age: Int): User = User.new {
            this.name = name
            this.age = age
        }
    }
    private var name by UserTable.name
    private var age by UserTable.age

    override fun toString(): String = "User(id=$id, name=$name, age=$age)"
}
