package com.example.models

import com.example.database.tables.Role
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: Int,
    val lastName: String,
    val firstName: String,
    val midlleName: String,
    val login: String,
    val password: String,
    val role: Role,
    val picture: String,
    val rating: Double
)

@Serializable
data class UserInfoModel(
    val lastName: String,
    val firstName: String,
    val midlleName: String,
    val picture: String,
    val rating: Double,
    val id: Int
)

@Serializable
data class UserTestsModel(
    val max: Double,
    val current: Double,
    val testTitle: String
)
