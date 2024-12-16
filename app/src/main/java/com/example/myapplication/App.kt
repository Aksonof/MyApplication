package com.example.myapplication

import android.app.Application
import com.example.myapplication.model.ContactRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    val contactRepository = ContactRepository()


}