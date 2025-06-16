package com.example.weatherdemo.view.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherdemo.R
import com.example.weatherdemo.databinding.ActivityForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

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
                forgerPasswordReset(email)
            }
        }

        binding.btnForgetOk.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)

    }

    fun forgerPasswordReset(mail: String){
        FirebaseAuth.getInstance().sendPasswordResetEmail(mail).addOnCompleteListener { task ->
            if(task.isSuccessful){
                binding.textView.visibility = View.VISIBLE
                binding.btnForgetOk.visibility = View.VISIBLE
                binding.btnNext.isEnabled = false
            }else{
                Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

// find id

// use binding