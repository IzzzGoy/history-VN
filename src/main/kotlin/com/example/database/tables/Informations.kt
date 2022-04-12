package com.example.database.tables

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Informations: IntIdTable() {
    val objectId = reference("object_id", Objects, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val years = integer("year_grounds")
    val description = text("description")
}

class Information(id: EntityID<Int>): IntEntity(id) {
    companion object: EntityClass<Int, Information>(Informations)

    var objectId by Object referencedOn Informations.objectId
    var years by Informations.years
    var description by Informations.description
}