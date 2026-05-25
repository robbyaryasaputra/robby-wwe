package com.example.rby_wwe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rby_wwe.databinding.ActivityAuthBinding
import com.example.rby_wwe.Home.pertemuan_3.RegisterActivity

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        // Cek status login
        val isLogin = sharedPref.getBoolean("isLogin", false)
        if (isLogin) {
            startActivity(Intent(this, BaseActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                sharedPref.edit().apply {
                    putBoolean("isLogin", true)
                    putString("username", username)
                    apply()
                }

                val intent = Intent(this, BaseActivity::class.java)
                intent.putExtra("USER_NAME", username)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Tolong isi Username dan Password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            // Berpindah ke halaman Register
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}