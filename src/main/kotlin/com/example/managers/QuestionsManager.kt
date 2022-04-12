package com.example.managers

import com.example.database.tables.*
import com.example.models.Answer
import com.example.models.QuestionModel
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class QuestionsManager {

    private val mapper = object {
        fun map(question: Question) = QuestionModel(
            id = question.id.value,
            answers = AnswerPossibles.find { AnswerPossibilities.question eq question.id }.map {
                Answer(
                    id = it.id.value,
                    text = it.possible
                )
            }
        )
    }

    suspend fun getByTest(id: Int) = newSuspendedTransaction {
        Test.findById(id)?.let {
            Question.find { Questions.test eq it.id }.map {
                mapper.map(it)
            }
        } ?: throw Exception("Test with id: $id not found")
    }
}