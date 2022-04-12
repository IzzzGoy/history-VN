package com.example.database.tables

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Objects : IntIdTable() {
    val title = text("object_title")
    val category = reference(
        "object_category", Categories,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val location = text("object_location")
}

class Object(id: EntityID<Int>): IntEntity(id) {
    companion object: EntityClass<Int, Object>(Objects)

    var title by Objects.title
    var category by Category referencedOn Objects.category
    var location by Objects.location

    var image by Image via ObjectsImages
}