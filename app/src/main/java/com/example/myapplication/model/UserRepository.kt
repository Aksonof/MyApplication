package com.example.myapplication.model

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {

    suspend fun registerUser(
        email: RequestBody,
        password: RequestBody,
        name: RequestBody?,
        phone: RequestBody?,
        address: RequestBody?,
        career: RequestBody?,
        birthday: RequestBody?,
        facebook: RequestBody?,
        instagram: RequestBody?,
        twitter: RequestBody?,
        linkedin: RequestBody?,
        image: MultipartBody.Part?
    ): Response<RegisterResponse> {
        return apiService.registerUser(
            email, password, name, phone, address, career, birthday,
            facebook, instagram, twitter, linkedin, image
        )
    }

    suspend fun loginUser(
        email: RequestBody,
        password: RequestBody
    ): Response<RegisterResponse> {
        return apiService.loginUser(email, password)
    }

    suspend fun getAllUsers(authHeader: String): Response<ApiResponse<UsersResponse>> {
        return apiService.getAllUsers(authHeader)
    }

    suspend fun getUserContacts(
        authHeader: String,
        userId: Int
    ): Response<ApiResponse<ContactsResponse>> {
        return apiService.getUserContacts(authHeader, userId)
    }

    suspend fun addContact(
        authHeader: String,
        userId: Int,
        contactId: Int
    ): Response<ApiResponse<UsersResponse>> {
        val body = """{"contactId": $contactId}"""
            .toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.addContact(authHeader, userId, body)
    }

    suspend fun deleteContact(
        authHeader: String,
        userId: String,
        contactId: String
    ): Response<ApiResponse<UsersResponse>> {
        return apiService.deleteContact(authHeader, userId, contactId)
    }

}
