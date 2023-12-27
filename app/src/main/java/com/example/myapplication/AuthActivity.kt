package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class AuthActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val userEmail =
            findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.emailEditText)
        val userPass =
            findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.passEditText)
        val registerButton =
            findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.registerButton)
        val intent = Intent(this, MainActivity::class.java)

        val checkBox = findViewById<CheckBox>(R.id.rememberMeCheckBox)
        sharedPref = getSharedPreferences("autoLog", MODE_PRIVATE)

        userEmail.setText(sharedPref.getString("email", ""))
        userPass.setText(sharedPref.getString("pass", ""))

        var email: String
        var pass: String

        registerButton.setOnClickListener {
            email = userEmail.text.toString()
            pass = userPass.text.toString()

            if (isValidEmail(userEmail.text.toString()) && isValidPass(userPass.text.toString())) {
                if (checkBox.isChecked) {
                    val editor = sharedPref.edit()
                    editor.putString("email", email)
                    editor.putString("pass", pass)
                    editor.apply()
                }
                intent.putExtra("email", email)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            } else {
                Toast.makeText(
                    this,
                    "Invalid email or password.", Toast.LENGTH_SHORT
                ).show()
            }
        }

        userEmail.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                email = userEmail.text.toString()
                if (isValidEmail(email)) {
                    true
                } else {
                    userEmail.error = "Use the correct email format"
                    true
                }
            } else {
                false
            }
        }

        userEmail.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                email = userEmail.text.toString()
                if (isValidEmail(email)) {
                    // to do
                } else {
                    userEmail.error = "Use the correct email format"
                }
            }
        }


        userPass.setOnEditorActionListener { _, actionId, event ->

            val passLayout =
                findViewById<TextInputLayout>(R.id.passInputLayout)

            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                pass = userPass.text.toString()

                if (isValidPass(pass)) {
                    true
                } else {

                    val handler = Handler()
                    passLayout.endIconMode = TextInputLayout.END_ICON_NONE
                    userPass.error = "Password: 6+ chars, Aa-Zz, 0-9"

                    handler.postDelayed({
                        userPass.error = null
                        passLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    }, 4000)


                    true
                }
            } else {
                false
            }
        }

        userPass.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                pass = userPass.text.toString()
                if (isValidPass(pass)) {
                    // to do
                } else {
                    val handler = Handler()
                    val passLayout =
                        findViewById<TextInputLayout>(R.id.passInputLayout)
                    passLayout.endIconMode = TextInputLayout.END_ICON_NONE

                    userPass.error = "Password: 6+ chars, Aa-Zz, 0-9"
                    handler.postDelayed({
                        userPass.error = null
                        passLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    }, 4000)
                }
            }
        }
    }


    private fun isValidPass(pass: String): Boolean {
        val regex = Regex("^[A-Za-z0-9]{6,}$")
        return regex.matches(pass)
    }

    private fun isValidEmail(email: String): Boolean {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

}