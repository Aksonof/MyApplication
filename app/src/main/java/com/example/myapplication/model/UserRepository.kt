package com.example.myapplication.model

import okhttp3.MultipartBody
import okhttp3.RequestBody
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



}
