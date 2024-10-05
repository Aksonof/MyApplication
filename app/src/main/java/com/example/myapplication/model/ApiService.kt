package com.example.myapplication.model

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("users")
    suspend fun registerUser(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody? = null,
        @Part("phone") phone: RequestBody? = null,
        @Part("address") address: RequestBody? = null,
        @Part("career") career: RequestBody? = null,
        @Part("birthday") birthday: RequestBody? = null,
        @Part("facebook") facebook: RequestBody? = null,
        @Part("instagram") instagram: RequestBody? = null,
        @Part("twitter") twitter: RequestBody? = null,
        @Part("linkedin") linkedin: RequestBody? = null,
        @Part image: MultipartBody.Part? = null
    ): Response<RegisterResponse>


}
