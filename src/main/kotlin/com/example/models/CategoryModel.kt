package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryModel(
    val id: Int,
    val title: String,
    val imageUrl: String
)
