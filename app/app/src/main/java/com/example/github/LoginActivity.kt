package com.example.github

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.github.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnLoginListener()
        txtRegisterListener()
    }

    private fun txtRegisterListener() {
        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun btnLoginListener() {
        binding.btnLogin.setOnClickListener {
            val email = binding.alUsername.text.toString().trim()
            val password = binding.alPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.alUsername.error = "Email Required"
                binding.alUsername.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.alPassword.error = "Password Required"
                binding.alPassword.requestFocus()
                return@setOnClickListener
            }

            if (email == "admin" && password == "123") {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}