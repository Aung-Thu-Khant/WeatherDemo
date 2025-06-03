package com.example.weatherdemo.view.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherdemo.R
import com.example.weatherdemo.databinding.ActivityForgetPasswordBinding

class ForgetPassword : AppCompatActivity() {

    private lateinit var binding: ActivityForgetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)

        binding.btnNext.setOnClickListener {
            var email = binding.etEmail.editText!!.text.toString()
            if(TextUtils.isEmpty(email)){
                binding.etEmail.error = "Email is required"
                binding.etEmail.requestFocus()
            } else {
               // send link to user mail
            }
        }

        binding.btnForgetOk.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)

    }
}

// find id

// use binding