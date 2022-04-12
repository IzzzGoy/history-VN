package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class QuestionModel(
    val id: Int,
    val answers: List<Answer>
)

@Serializable
data class Answer(
    val id: Int,
    val text: String
)
