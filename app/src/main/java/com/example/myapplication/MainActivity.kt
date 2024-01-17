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

    /*
    * It removes numeric digits from the email and processes the local part before the '@' symbol.
    * If the local part doesn't contain underscores or dots, it returns the unmodified local part.
    *
    * If the local part contains underscores or dots, it separates it into a first name and
    * last name, replaces underscores with dots, and capitalizes the first letter of each part.
    * Finally, it combines the first and last names and returns the full name.
    * */
    private fun getNameFromEmail(email: String): String {

        val emailWithOutNumbers = email.replace(Regex("\\d"), "")

        if (!Regex("[._]").matches(emailWithOutNumbers.substringBefore('@'))) {
            return emailWithOutNumbers.substringBefore('@')
        }

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