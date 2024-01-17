package com.example.myapplication

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
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

        var email: String
        var pass: String
        val emailView =
            findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.emailEditText)
        val passView =
            findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.passEditText)
        val registerButton =
            findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.registerButton)
        val checkBox = findViewById<CheckBox>(R.id.rememberMeCheckBox)
        val intent = Intent(this, MainActivity::class.java)
        sharedPref = getSharedPreferences("autoLog", MODE_PRIVATE)

        emailView.setText(sharedPref.getString("email", ""))
        passView.setText(sharedPref.getString("pass", ""))

        /*
        * This code handles the registration button click. It reads email and password input,
        * checks if they are correct, saves them in SharedPreferences if a checkbox is checked,
        * and starts a new activity with custom animation. If the data is invalid,
        * it displays an error toast.
        * */
        registerButton.setOnClickListener {
            email = emailView.text.toString()
            pass = passView.text.toString()

            if (isValidEmail(email) && isValidPass(pass)) {
                if (checkBox.isChecked) {
                    val editor = sharedPref.edit()
                    editor.putString("email", email)
                    editor.putString("pass", pass)
                    editor.apply()
                }
                intent.putExtra("email", email)
                val options =
                    ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
                startActivity(intent, options.toBundle())
            } else {
                Toast.makeText(
                    this,
                    "Invalid email or password.", Toast.LENGTH_SHORT
                ).show()
            }
        }

        /*
        * This code handles the "Done" or "Enter" click. It validates the entered email, displays
        * an error message if it's invalid, and returns true.
        * If the action is not "Done" or "Enter," it returns false.
        * */
        emailView.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                if (!isValidEmail(emailView.text.toString())) {
                    emailView.error = "Use the correct email format"
                }
                true
            } else {
                false
            }
        }

        /*
        * When the field loses focus, it checks if the entered email is valid.
        * If not, it sets an error message.
        * */
        emailView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (!isValidEmail(emailView.text.toString())) {
                    emailView.error = "Use the correct email format"
                }
            }
        }

        /*
        * This code handles the "Done" or "Enter" click. It validates the entered pass,
        * removes the icon password toggle for 6 seconds to show the password format and then
        * sets it back.
        * If the action is not "Done" or "Enter," it returns false.
        * */
        passView.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                if (!isValidPass(passView.text.toString())) {
                    val passLayout =
                        findViewById<TextInputLayout>(R.id.passInputLayout)
                    val handlerThread = HandlerThread("MyHandlerThread")
                    handlerThread.start()
                    val handler = Handler(handlerThread.looper)
                    passLayout.endIconMode = TextInputLayout.END_ICON_NONE
                    passView.error = "Password: 6+ chars, Aa-Zz, 0-9"
                    handler.postDelayed({
                        passView.error = null
                        passLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE

                    }, 6000)
                    handlerThread.quitSafely()
                }
                true
            } else {
                false
            }
        }

        /*
        * When the field loses focus, it validates the entered pass, removes the icon password
        * toggle for 6 seconds to show the password format and then sets it back.
        * */
        passView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (!isValidPass(passView.text.toString())) {
                    val passLayout =
                        findViewById<TextInputLayout>(R.id.passInputLayout)
                    val handlerThread = HandlerThread("MyHandlerThread")
                    handlerThread.start()
                    val handler = Handler(handlerThread.looper)
                    passLayout.endIconMode = TextInputLayout.END_ICON_NONE
                    passView.error = "Password: 6+ chars, Aa-Zz, 0-9"
                    handler.postDelayed({
                        passView.error = null
                        passLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    }, 6000)
                    handlerThread.quitSafely()
                }
            }
        }
    }

    /*
    * It uses a regular expression to ensure that the password contains at least 6 characters,
    * including a mix of uppercase and lowercase letters (Aa-Zz) and numeric digits (0-9).
    * */
    private fun isValidPass(pass: String): Boolean {
        val regex = Regex("^[A-Za-z0-9]{6,}$")
        return regex.matches(pass)
    }

    /*
    *  It returns true if the email is not empty and matches the standard email address pattern;
    * otherwise, it returns false.
    * */
    private fun isValidEmail(email: String): Boolean {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }
}