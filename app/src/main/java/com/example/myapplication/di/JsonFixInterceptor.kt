package com.example.myapplication.di

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class JsonFixInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val originalBody = originalResponse.body

        val originalContent = originalBody.string()

        val fixedContent = fixInvalidJson(originalContent)

        val newBody = fixedContent.toResponseBody(originalBody.contentType())
        return originalResponse.newBuilder().body(newBody).build()
    }

    private fun fixInvalidJson(json: String): String {
        return if (json.trim().takeLast(2) == "}}") {
            json.trim()
        } else {
            json.trim() + "}"
        }
    }

}
