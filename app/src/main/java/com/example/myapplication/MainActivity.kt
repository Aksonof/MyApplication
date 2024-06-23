package com.example.myapplication

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        processUserName()
        logOutClickListener()

    }

    private fun logOutClickListener() {
        binding.logOutViewButton.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            val options =
                ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
            intent.putExtra("logOut", true)
            startActivity(intent, options.toBundle())
        }
    }

    private fun processUserName() {
        val email = intent.extras?.getString("email") ?: "John Doe"
        binding.userNameTextView.text = getNameFromEmail(email)
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

        val name = email
            .replace(Regex("\\d"), "")
            .substringBefore('@')
            .replace('_', '.')

        if (name.isEmpty()) {
            return "John Doe"
        } else if (!name.contains(".")) {
            return name.replaceFirstChar { Character.toUpperCase(name[0]) }
        }

        var firstName = name.substringBefore('.')
        firstName = firstName.replaceFirstChar { Character.toUpperCase(firstName[0]) }

        var lastName = name.substringAfter('.')
        lastName = lastName.replaceFirstChar { Character.toUpperCase(lastName[0]) }

        return "$firstName $lastName"
    }

}