package com.example.database.tables

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Categories : IntIdTable() {
    val title = text("category_title")
    val image = reference(
        "category_image", Images,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
}

class Category(id: EntityID<Int>): IntEntity(id) {
    companion object: EntityClass<Int, Category>(Categories)

    val title by Categories.title
    val image by Image referencedOn  Categories.image
}