package com.example.rby_wwe.pertemuan_2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rby_wwe.databinding.ActivityHitungBinding

class HitungActivity : AppCompatActivity() {

    // 1. Inisialisasi View Binding
    private lateinit var binding: ActivityHitungBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. Setup Binding
        binding = ActivityHitungBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val paddingTambahan = (24 * resources.displayMetrics.density).toInt()
            v.setPadding(
                systemBars.left + paddingTambahan,
                systemBars.top + paddingTambahan,
                systemBars.right + paddingTambahan,
                systemBars.bottom + paddingTambahan
            )
            insets
        }

        // 3. Logika Hitung Segitiga (Menggunakan Binding)
        binding.btnSegitiga.setOnClickListener {
            val alasTeks = binding.inputAlas.text.toString()
            val tinggiTeks = binding.inputTinggi.text.toString()

            if (alasTeks.isNotEmpty() && tinggiTeks.isNotEmpty()) {
                val alas = alasTeks.toDouble()
                val tinggi = tinggiTeks.toDouble()
                val luas = 0.5 * alas * tinggi
                binding.teksHasil.text = "Luas Segitiga: $luas"
                Log.d("Praktikum", "Berhasil hitung segitiga: $luas")
            } else {
                Toast.makeText(this, "Alas dan Tinggi harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        // 4. Logika Hitung Kubus (Menggunakan Binding)
        binding.btnKubus.setOnClickListener {
            val sisiTeks = binding.inputSisi.text.toString()

            if (sisiTeks.isNotEmpty()) {
                val sisi = sisiTeks.toDouble()
                val volume = sisi * sisi * sisi
                binding.teksHasil.text = "Volume Kubus: $volume"
                Log.d("Praktikum", "Berhasil hitung kubus: $volume")
            } else {
                Toast.makeText(this, "Sisi harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        // 5. Logika Tombol Kembali
        binding.btnBack.setOnClickListener {
            finish() // Menutup halaman hitung dan kembali ke Dashboard/Welcome screen
        }
    }
}