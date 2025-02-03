package com.example.myapplication.model

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

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

    @Multipart
    @POST("login")
    suspend fun loginUser(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody
    ): Response<RegisterResponse>

    @GET("users")
    suspend fun getAllUsers(
        @Header("Authorization") authHeader: String
    ): Response<ApiResponse<UsersResponse>>

    @GET("users/{userId}/contacts")
    suspend fun getUserContacts(
        @Header("Authorization") authHeader: String,
        @Path("userId") userId: Int
    ): Response<ApiResponse<ContactsResponse>>

    @PUT("users/{userId}/contacts")
    suspend fun addContact(
        @Header("Authorization") authHeader: String,
        @Path("userId") userId: Int,
        @Body body: RequestBody
    ): Response<ApiResponse<UsersResponse>>

    @DELETE("users/{userId}/contacts/{contactId}")
    suspend fun deleteContact(
        @Header("Authorization") authHeader: String,
        @Path("userId") userId: String,
        @Path("contactId") contactId: String
    ): Response<ApiResponse<UsersResponse>>
}
