package com.example.myapplication

import android.app.Application
import com.example.myapplication.model.ApiClient
import com.example.myapplication.model.ApiService
import com.example.myapplication.model.ContactRepository
import com.example.myapplication.model.UserRepository
import com.example.myapplication.viewModel.SessionManager

class App: Application() {

    val contactRepository = ContactRepository()

    private val apiClient: ApiService by lazy { ApiClient.create() }
    val userRepository by lazy { UserRepository(apiClient) }
    val sessionManager by lazy { SessionManager(this) }

}