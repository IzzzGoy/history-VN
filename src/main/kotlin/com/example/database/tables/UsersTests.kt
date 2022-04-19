package com.example.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object UsersTests: Table() {
    val user = reference("user_t", Users, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val test = reference("test", Tests, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val current = double("current").default(0.0)
    override val primaryKey: PrimaryKey = PrimaryKey(user, test, name = "Users_tests_pk")
}