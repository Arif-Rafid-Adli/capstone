package com.example.github

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.github.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        txtLoginListener()
        btnRegisterListener()
    }

    private fun btnRegisterListener() {
        binding.btnRegister.setOnClickListener {
            val name = binding.reName.text.toString().trim()
            val email = binding.reEmail.text.toString().trim()
            val password = binding.rePassword.text.toString().trim()
            val conpass = binding.reConpassword.text.toString().trim()

            if(name.isEmpty()){
                binding.reName.error = "Name Required"
                binding.reName.requestFocus()
                return@setOnClickListener
            }else if(email.isEmpty()){
                binding.reEmail.error = "Email Required"
                binding.reEmail.requestFocus()
                return@setOnClickListener
            }else if(password.isEmpty()){
                binding.rePassword.error = "Password Required"
                binding.rePassword.requestFocus()
                return@setOnClickListener
            }else if(conpass.isEmpty()){
                binding.reConpassword.error = "Confirm Password Required"
                binding.reConpassword.requestFocus()
                return@setOnClickListener
            }else if(password != conpass){
                binding.reConpassword.error = "Password do not match"
                binding.reConpassword.requestFocus()
                return@setOnClickListener
            }else{
                Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }

    private fun txtLoginListener() {
        binding.txtLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}