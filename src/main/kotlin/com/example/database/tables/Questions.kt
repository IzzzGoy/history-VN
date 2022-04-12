package com.example.database.tables

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Questions: IntIdTable() {
    val question = text("question_text")
    val test = reference("test", Tests, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.CASCADE)
}

class Question(id: EntityID<Int>): IntEntity(id) {
    companion object: EntityClass<Int, Question>(Questions)

    var question by Questions.question
    var test by Test referencedOn Questions.test
}