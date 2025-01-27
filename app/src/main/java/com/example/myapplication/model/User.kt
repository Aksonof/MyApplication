package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
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
    val imageUrl: String?,
    var isSelected: Boolean?
) : Parcelable

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

data class ApiResponse<T>(
    val status: String,
    val code: Int,
    val message: String?,
    val data: T
)

data class ContactsResponse(
    val users: List<User>
)
