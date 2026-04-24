package com.example.rby_wwe.pertemuan_3

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rby_wwe.R // Pastikan import R benar
import com.example.rby_wwe.databinding.ActivityLoginBinding
import com.example.rby_wwe.pertemuan_4.DashboardActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Logika Klik Tombol Loginn
        binding.btnLogin.setOnClickListener {
            val email = binding.etUsername.text.toString() // ID tetap etUsername, tapi teksnya "E-mail"
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Berpindah ke WelcomeActivity
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("USER_NAME", email) // Kirim data email (username) ke hal berikutnya
                startActivity(intent)
            } else {
                Toast.makeText(this, "Tolong isi E-mail dan Password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}