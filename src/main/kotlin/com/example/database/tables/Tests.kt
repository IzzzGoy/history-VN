package com.example.database.tables

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Tests: IntIdTable() {
    val title = text("test_title")
    val category = reference(
        "test_category", Categories,
        onUpdate = ReferenceOption.CASCADE,
        onDelete = ReferenceOption.CASCADE
    )
}

class Test(id: EntityID<Int>): IntEntity(id) {
    companion object: EntityClass<Int, Test>(Tests)

    val title by Tests.title
    val category by Category referencedOn Tests.category

    var users by User via UsersTests
}