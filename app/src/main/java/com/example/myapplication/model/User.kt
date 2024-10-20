package com.example.myapplication.model

data class User(
    val email: String,
    val name: String?,
    val phone: String?,
    val address: String?,
    val career: String?,
    val birthday: String?,
    val facebook: String?,
    val instagram: String?,
    val twitter: String?,
    val linkedin: String?,
    val imageUrl: String?
)

data class RegisterResponse(
    val status: String,
    val code: Int,
    val data: UserData
)

data class UserData(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)