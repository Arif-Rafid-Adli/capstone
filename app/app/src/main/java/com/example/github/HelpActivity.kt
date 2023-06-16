package com.example.github

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.github.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnWA.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                sendMessage()
            }
        })

    }

    private fun sendMessage() {
        val contact = "+62 85157443860"
        val wa = Intent(Intent.ACTION_SEND)
        wa.type = "text/plain"
        wa.setPackage("https://api.whatsapp.com/send?phone=$contact")
        wa.putExtra(Intent.EXTRA_TEXT, "Halo, saya mau bertanya mengenai aplikasi Satu Darah")

        if(wa.resolveActivity(packageManager)==null){
            Toast.makeText(this, "Please install whatsapp", Toast.LENGTH_LONG).show()
            return
        }
        startActivity(wa)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}