package com.example.myapplication

import android.app.Application
import com.example.myapplication.model.ContactRepository

class App: Application() {

    val contactRepository = ContactRepository()
}