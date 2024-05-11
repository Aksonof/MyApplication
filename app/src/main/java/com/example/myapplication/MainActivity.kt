package com.example.myapplication

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    /*
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
        }*/

}