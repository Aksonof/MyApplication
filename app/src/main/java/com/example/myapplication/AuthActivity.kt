package com.example.myapplication

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        autoLog()
        registerClickListener()
        emailKeyListener()
        emailFocusListener()
        passKeyListener()
        passFocusListener()
    }

    private fun autoLog() {
        sharedPref = getSharedPreferences("autoLog", MODE_PRIVATE)
        binding.emailEditText.setText(sharedPref.getString("email", ""))
        binding.passEditText.setText(sharedPref.getString("pass", ""))

        if (intent.extras?.getBoolean("logOut") == true) {
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()
        }

        if (binding.emailEditText.text?.isNotBlank() == true &&
            intent.extras?.getBoolean("logOut") != true
        ) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", sharedPref.getString("email", ""))
            startActivity(intent)
        }
    }

    /*
    * This code validates the entered password when the field loses focus.
    * */
    private fun passFocusListener() {
        binding.passEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (!isValidPass(binding.passEditText.text.toString())) {
                    binding.passInputLayout.error = getString(R.string.passMSG)
                } else {
                    binding.passInputLayout.error = null
                }
            }
        }
    }

    /*
    * This code manages the click event for "Done" or "Enter" and validates the entered password.
    * If the action is neither "Done" nor "Enter," the function returns fal
    * */
    private fun passKeyListener() {
        binding.passEditText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                if (!isValidPass(binding.passEditText.text.toString())) {
                    binding.passInputLayout.error = getString(R.string.passMSG)
                } else {
                    binding.passInputLayout.error = null
                }
                true
            } else {
                false
            }
        }
    }


    /*
     * When the field loses focus, it checks if the entered email is valid.
     * If not, it sets an error message.
     * */
    private fun emailFocusListener() {
        binding.emailEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (!isValidEmail(binding.emailEditText.text.toString())) {
                    binding.emailEditText.error = getString(R.string.emailMSG)
                }
            }
        }
    }

    /*
    * This code handles the "Done" or "Enter" click. It validates the entered email, displays
    * an error message if it's invalid, and returns true.
    * If the action is not "Done" or "Enter," it returns false.
    * */
    private fun emailKeyListener() {
        binding.emailEditText.setOnKeyListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                if (!isValidEmail(binding.emailEditText.text.toString())) {
                    binding.emailEditText.error = getString(R.string.emailMSG)
                }
                true
            } else {
                false
            }
        }
    }

    /*
    * This code handles the registration button click. It reads email and password input,
    * checks if they are correct, saves them in SharedPreferences if a checkbox is checked,
    * and starts a new activity with custom animation. If the data is invalid,
    * it displays an error toast.
    * */
    private fun registerClickListener() {
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val email = binding.emailEditText.text.toString()
            val pass = binding.passEditText.text.toString()
            val editor = sharedPref.edit()

            if (isValidEmail(email) && isValidPass(pass)) {
                if (binding.rememberMeCheckBox.isChecked) {
                    editor.putString("email", email)
                    editor.putString("pass", pass)
                    editor.apply()
                } else {
                    editor.clear()
                    editor.apply()
                }
                intent.putExtra("email", email)
                val options =
                    ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
                startActivity(intent, options.toBundle())
            } else {
                Toast.makeText(
                    this, getString(R.string.finalValidationMSG), Toast.LENGTH_SHORT
                ).show()
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