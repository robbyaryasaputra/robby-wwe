package com.example.rby_wwe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rby_wwe.Home.pertemuan_3.RegisterActivity
import com.example.rby_wwe.databinding.ActivityAuthBinding

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

        // Cek Auto Login
        val isLogin = sharedPref.getBoolean("isLogin", false)
        if (isLogin) {
            val intent = Intent(this, BaseActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Logika Login
        binding.loginButton.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Ambil data hasil registrasi
                val registeredUser = sharedPref.getString("reg_username", "")
                val registeredPass = sharedPref.getString("reg_password", "")

                // Rule Login Quiz:
                // 1. username == password
                // 2. data cocok dengan SharedPreferences (Hasil Registrasi)
                if (username == password || (username == registeredUser && password == registeredPass)) {
                    val editor = sharedPref.edit()
                    editor.putBoolean("isLogin", true)
                    editor.putString("username", username)
                    editor.apply()

                    val intent = Intent(this, BaseActivity::class.java)
                    intent.putExtra("USER_NAME", username)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Username atau Password salah", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Tolong isi Username dan Password", Toast.LENGTH_SHORT).show()
            }
        }

        // PERBAIKAN: Navigasi ke Halaman Registrasi
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}


