package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = intent.extras?.getString("email") ?: "Username"
        val name = findViewById<TextView>(R.id.name)
        name.text = getNameFromEmail(email)

    }

    private fun getNameFromEmail(email: String): String {

        val emailWithOutNumbers = email.replace(Regex("\\d"), "")

        var firstName = emailWithOutNumbers.substringBefore('@')
            .replace('_', '.')
            .substringBefore('.')
        firstName = firstName.replaceFirstChar { Character.toUpperCase(firstName[0]) }

        var lastName = emailWithOutNumbers.substringBefore('@')
            .replace('_', '.')
            .substringAfter('.')
        lastName = lastName.replaceFirstChar { Character.toUpperCase(lastName[0]) }
        return "$firstName $lastName"
    }

}