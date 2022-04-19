package com.example.database.tables

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object AnswerPossibilities: IntIdTable() {
    val possible = text("possible")
    val question = reference("question", Questions, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.CASCADE)
    val isRight = bool("is_answer_right")
}

class AnswerPossibles(id: EntityID<Int>): IntEntity(id) {
    companion object: EntityClass<Int, AnswerPossibles>(AnswerPossibilities)

    var possible by AnswerPossibilities.possible
    var question by Question referencedOn AnswerPossibilities.question
    var isRight by AnswerPossibilities.isRight
}