package com.example.myapplication

import android.app.Application
import com.example.myapplication.model.UsersManager

class App: Application() {

    val usersManager = UsersManager()
}