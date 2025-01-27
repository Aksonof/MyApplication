package com.example.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.RegisterResponse
import com.example.myapplication.model.User
import com.example.myapplication.model.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
): ViewModel() {

    private val _registerState = MutableLiveData<Result<RegisterResponse>>()
    val registerState: LiveData<Result<RegisterResponse>> get() = _registerState

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _allContactsLiveData = MutableLiveData<List<User>>()
    val allContactsLiveData: LiveData<List<User>> = _allContactsLiveData

    private val _addedContacts = MutableLiveData<List<User>>(emptyList())
    val addedContacts: LiveData<List<User>> = _addedContacts

    fun cutContacts() {
        val currentContacts = _allContactsLiveData.value

        if (currentContacts != null) {
            val limitedContacts = currentContacts.take(50)
            _allContactsLiveData.value = limitedContacts
        } else {

            Log.d("UserViewModel", "We have null")
        }
    }

    fun loginUser(
        email: String,
        password: String
    ) = viewModelScope.launch {
        try {
            val emailRequestBody = createRequestBody(email)
            val passwordRequestBody = createRequestBody(password)
            val response = userRepository.loginUser(emailRequestBody, passwordRequestBody)
            processResponse(response)

        } catch (e: Exception) {
            _registerState.value = Result.failure(e)
        }
    }

    fun registerUser(
        email: String,
        password: String,
        name: String?,
        phone: String?,
        address: String?,
        career: String?,
        birthday: String?,
        facebook: String?,
        instagram: String?,
        twitter: String?,
        linkedin: String?,
        image: File?
    ) = viewModelScope.launch {
        try {
            val response = userRepository.registerUser(
                createRequestBody(email),
                createRequestBody(password),
                createRequestBody(name ?: ""),
                createRequestBody(phone ?: ""),
                createRequestBody(address ?: ""),
                createRequestBody(career ?: ""),
                createRequestBody(birthday ?: ""),
                createRequestBody(facebook ?: ""),
                createRequestBody(instagram ?: ""),
                createRequestBody(twitter ?: ""),
                createRequestBody(linkedin ?: ""),
                image?.let {
                    val requestFile = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("image", it.name, requestFile)
                }
            )
            processResponse(response)
        } catch (e: Exception) {
            _registerState.value = Result.failure(e)
        }
    }

    private fun processResponse(response: Response<RegisterResponse>) {
        if (response.isSuccessful) {
            val registerResponse = response.body()

            registerResponse?.let {
                _registerState.value = Result.success(it)
                _user.value = it.data.user
                sessionManager.saveTokens(it.data.accessToken, it.data.refreshToken)
                val token = sessionManager.getAccessToken().toString()

                getAddedContacts(
                    "Bearer $token",
                    it.data.user.id.toString()
                )
                getAllContacts("Bearer $token")

            } ?: run {
                _registerState.value = Result.failure(Exception("Response body is null"))
            }
        } else {
            _registerState.value =
                Result.failure(Exception("Registration failed: ${response.code()}"))
        }
    }

    private fun createRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }


    private fun getAllContacts(authHeader: String) = viewModelScope.launch {
        val response = userRepository.getAllUsers(authHeader)
        if (response.isSuccessful) {
            _allContactsLiveData.value = (response.body()?.data?.users)
            Log.d("Sieee", "Size ${_allContactsLiveData.value?.size}")
        }
        Log.d(
            "AAArrr", "is Successful?: ${response.isSuccessful}" +
                    "Status: ${response.body()?.status}" +
                    "User: ${response.body()?.data?.users?.size}"
        )
    }

    private fun getAddedContacts(authHeader: String, userId: String) = viewModelScope.launch {
        val response = userRepository.getUserContacts(authHeader, userId)
        if (response.isSuccessful) {
            _addedContacts.value = response.body()?.data?.users
        }
    }

    fun deleteContact(contact: User) = viewModelScope.launch {
        val response = userRepository.deleteContact(
            "Bearer ${sessionManager.getAccessToken()}",
            user.value?.id.toString(),
            contact.id.toString()
        )
        if (response.isSuccessful) {
            getAddedContacts(
                "Bearer ${sessionManager.getAccessToken()}",
                user.value?.id.toString()
            )
        }
    }

    fun addContact(contact: User) = viewModelScope.launch {
        val response = userRepository.addContact(
            "Bearer ${sessionManager.getAccessToken()}",
            user.value?.id.toString(), contact.id
        )
        if (response.isSuccessful) {
            getAddedContacts(
                "Bearer ${sessionManager.getAccessToken()}",
                user.value?.id.toString()
            )
        }
    }

    fun isContactAdded(contact: User): Boolean {
        return _addedContacts.value?.any { it.email == contact.email } == true
    }
}