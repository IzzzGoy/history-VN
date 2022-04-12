package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class QuestionModel(
    val id: Int,
    val answers: List<AnswerModel>
)

@Serializable
data class AnswerModel(
    val id: Int,
    val text: String,
    val isRight: Boolean
)

@Serializable
data class AnswersModel(
    val user: Int,
    val test: Int,
    val  answers: List<Boolean>
)
