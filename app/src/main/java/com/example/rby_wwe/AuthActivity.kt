package com.example.rby_wwe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rby_wwe.databinding.ActivityLoginBinding
import com.example.rby_wwe.pertemuan_4.DashboardActivity

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Definisikan SharedPreferences (sesuai gambar: user_pref)
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        // 1. Kondisi jika isLogin bernilai true
        val isLogin = sharedPref.getBoolean("isLogin", false)
        if (isLogin) {
            // Panggil Intent untuk ke DashboardActivity
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Logika Klik Tombol Login
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // 2. Set isLogin menjadi true dan simpan username jika berhasil
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.apply()

                // Berpindah ke DashboardActivity
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("USER_NAME", username)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Tolong isi Username dan Password", Toast.LENGTH_SHORT).show()
            }
        }

        // Logika Klik Daftar
        binding.tvRegister.setOnClickListener {
            Toast.makeText(this, "Fitur Daftar belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }
}