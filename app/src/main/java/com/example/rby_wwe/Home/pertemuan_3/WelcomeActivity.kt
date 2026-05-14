package com.example.rby_wwe.Home.pertemuan_3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rby_wwe.Home.pertemuan_2.HitungActivity
import com.example.rby_wwe.Home.pertemuan_4.halaman2Activity
import com.example.rby_wwe.Home.pertemuan_4.halaman3Activity
import com.example.rby_wwe.databinding.ActivityWelcomeBinding // Import Binding
import com.google.android.material.snackbar.Snackbar

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mengambil data yang dikirim dari LoginActivity
        val userName = intent.getStringExtra("USER_NAME")
        binding.tvUserDisplay.text = userName

        val title = "Halaman Utama"
        val desc = "Ini adalah deskripsi dari halaman utama yang dibawa ke halaman lain."

        binding.btnRumus.setOnClickListener {
            val intent = Intent(this, HitungActivity::class.java)
            intent.putExtra("TITLE", title)
            intent.putExtra("DESC", desc)
            startActivity(intent)
        }

        binding.btnCustom1.setOnClickListener {
            val intent = Intent(this, halaman2Activity::class.java)
            intent.putExtra("TITLE", title)
            intent.putExtra("DESC", desc)
            startActivity(intent)
        }

        binding.btnCustom2.setOnClickListener {
            val intent = Intent(this, halaman3Activity::class.java)
            intent.putExtra("TITLE", title)
            intent.putExtra("DESC", desc)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya") { _, _ ->
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                    Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
                }
                .show()
        }
    }
}