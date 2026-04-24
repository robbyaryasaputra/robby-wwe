package com.example.rby_wwe.pertemuan_4

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rby_wwe.databinding.ActivityHalaman3Binding

class halaman3Activity : AppCompatActivity() {

    // 1. Deklarasi binding
    private lateinit var binding: ActivityHalaman3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. Inisialisasi binding
        binding = ActivityHalaman3Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 3. Menangkap data dari Intent
        val title = intent.getStringExtra("TITLE")
        val desc = intent.getStringExtra("DESC")

        // 4. Menampilkan data menggunakan binding
        binding.tvTitle.text = title ?: "Halaman 3"
        binding.tvDesc.text = desc ?: "Deskripsi tidak tersedia."

        // 5. Fungsi tombol kembali
        binding.btnBack.setOnClickListener {
            finish() // Menutup activity ini dan kembali ke Dashboard
        }
    }
}