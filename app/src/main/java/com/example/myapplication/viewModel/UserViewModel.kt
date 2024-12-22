package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.RegisterResponse
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
            val emailRequestBody = createRequestBody(email)
            val passwordRequestBody = createRequestBody(password)
            val nameRequestBody = createRequestBody(name ?: "")
            val phoneRequestBody = createRequestBody(phone ?: "")
            val addressRequestBody = createRequestBody(address ?: "")
            val careerRequestBody = createRequestBody(career ?: "")
            val birthdayRequestBody = createRequestBody(birthday ?: "")
            val facebookRequestBody = createRequestBody(facebook ?: "")
            val instagramRequestBody = createRequestBody(instagram ?: "")
            val twitterRequestBody = createRequestBody(twitter ?: "")
            val linkedinRequestBody = createRequestBody(linkedin ?: "")

            val imagePart = image?.let {
                val requestFile = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image", it.name, requestFile)
            }

            val response = userRepository.registerUser(
                emailRequestBody,
                passwordRequestBody,
                nameRequestBody,
                phoneRequestBody,
                addressRequestBody,
                careerRequestBody,
                birthdayRequestBody,
                facebookRequestBody,
                instagramRequestBody,
                twitterRequestBody,
                linkedinRequestBody,
                imagePart
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
}