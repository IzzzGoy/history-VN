package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class TestModel(
    val id: Int,
    val title: String,
    val category: Int
)

@Serializable
data class TestModelWithResult(
    val id: Int,
    val title: String,
    val category: Int,
    val current: Double,
    val max: Double
)
