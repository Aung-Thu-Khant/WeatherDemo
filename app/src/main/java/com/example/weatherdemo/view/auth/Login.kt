package com.example.weatherdemo.view.auth

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherdemo.MainActivity
import com.example.weatherdemo.R
import com.example.weatherdemo.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        val shp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        val info = shp.getBoolean("saveLogin", false)

        if(info){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            var email = binding.etEmail.editText!!.text.toString()
            var password = binding.etPassword.editText!!.text.toString()

            if (TextUtils.isEmpty(email)) {
                binding.etEmail.error = "Email is required"
                binding.etEmail.requestFocus()
            } else if (TextUtils.isEmpty(password)) {
                binding.etPassword.error = "Password is required"
                binding.etPassword.requestFocus()
            } else {
                // Login code
                loginAccount(email,password)
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

    fun loginAccount(mail: String, password: String){
        if ( binding.chLoginSave.isChecked ) {
            val sharePreference = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
            val editor = sharePreference.edit()
            editor.putBoolean("saveLogin", true)
            editor.commit()
        }
        if (checkInternetConnection(this)){
            //Login
            FirebaseAuth.getInstance().signInWithEmailAndPassword(mail,password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
                }
            }
        }else {
            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
        }
    }


    fun checkInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


}

//find id
// 1. initialize
// 2. find id


//binding
// just only one time initialize