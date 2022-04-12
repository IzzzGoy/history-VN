package com.example.database.tables

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Function

object Users : IntIdTable() {
    val lastName = text("user_last_name")
    val firstName = text("user_first_name")
    val midlleName = text("user_midlle_name")
    val login = text("user_login")
    val password = text("user_password")
    val role = enumeration("user_role", Role::class)
    val pictureId = reference(
        "user_avatar", Images,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val rating = double("user_rating")
}

@Serializable
enum class Role {
    ADMIN, USER
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, User>(Users)

    var lastName by Users.lastName
    var firstName by Users.firstName
    var midlleName by Users.midlleName
    var login by Users.login
    var password by Users.password
    var role by Users.role
    var pictureId by Image referencedOn Users.pictureId
    var rating by Users.rating

    var tests by Test via UsersTests
}