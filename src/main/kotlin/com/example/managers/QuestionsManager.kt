package com.example.managers

import com.example.database.tables.*
import com.example.models.AnswerModel
import com.example.models.AnswersModel
import com.example.models.QuestionModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class QuestionsManager {

    private val mapper = object {
        fun map(question: Question) = QuestionModel(
            id = question.id.value,
            title = question.question,
            answers = AnswerPossibles.find { AnswerPossibilities.question eq question.id }.map {
                AnswerModel(
                    id = it.id.value,
                    text = it.possible,
                    isRight = it.isRight
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

    suspend fun checkResults(answers: AnswersModel) = newSuspendedTransaction {
        UsersTests.update( {
            (UsersTests.user eq User[answers.user].id) and (UsersTests.test eq Test[answers.test].id)
        }) { it ->

            it[current] = (answers.answers.map { if (it) 1.0 else 0.0 }.let { it.sum() / it.size * 100})
        }
    }
}