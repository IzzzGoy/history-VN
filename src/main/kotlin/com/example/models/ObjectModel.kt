package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ObjectModel(
    val id: Int,
    val title: String,
    val category: Int,
    val location: String,
    val images: List<ImageModel>
)
