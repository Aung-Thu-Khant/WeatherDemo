package com.example.weatherdemo.view.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherdemo.MainActivity
import com.example.weatherdemo.R
import com.example.weatherdemo.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputLayout

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.btnLogin.setOnClickListener {
            var email = binding.etEmail.editText!!.text.toString()
            var password = binding.etPassword.editText!!.text.toString()

            // TextUtils.isEmpty(email)
            // email.isEmpty()

            if (TextUtils.isEmpty(email)) {
                binding.etEmail.error = "Email is required"
                binding.etEmail.requestFocus()
            } else if (TextUtils.isEmpty(password)) {
                binding.etPassword.error = "Password is required"
                binding.etPassword.requestFocus()
            } else {
                // Login code
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        binding.txtForget.setOnClickListener {
            val intent = Intent(this, ForgetPassword::class.java)
            startActivity(intent)
        }

        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)

    }
}

//find id
// 1. initialize
// 2. find id


//binding
// just only one time initialize