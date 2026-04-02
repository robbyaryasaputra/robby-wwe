package com.example.rby_wwe.pertemuan_2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rby_wwe.R

class HitungActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Kembalikan fitur ini agar area jam & baterai terlihat menyatu (modern)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hitung)

        // 2. Atur ulang padding: Gabungkan jarak sistem (jam) + jarak desain kita (24dp)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Konversi 24dp (dari XML) menjadi ukuran Pixel (px) agar bisa dibaca oleh Kotlin
            val paddingTambahan = (24 * resources.displayMetrics.density).toInt()

            v.setPadding(
                systemBars.left + paddingTambahan,   // Jarak kiri
                systemBars.top + paddingTambahan,    // Jarak atas (jam/baterai)
                systemBars.right + paddingTambahan,  // Jarak kanan
                systemBars.bottom + paddingTambahan  // Jarak bawah (navigasi)
            )
            insets
        }

        // 3. KENALKAN KOMPONEN XML KE KOTLIN
        val inputAlas = findViewById<EditText>(R.id.inputAlas)
        val inputTinggi = findViewById<EditText>(R.id.inputTinggi)
        val btnSegitiga = findViewById<Button>(R.id.btnSegitiga)

        val inputSisi = findViewById<EditText>(R.id.inputSisi)
        val btnKubus = findViewById<Button>(R.id.btnKubus)

        val teksHasil = findViewById<TextView>(R.id.teksHasil)

        // 4. KETIKA TOMBOL SEGITIGA DIKLIK
        btnSegitiga.setOnClickListener {
            val alasTeks = inputAlas.text.toString()
            val tinggiTeks = inputTinggi.text.toString()

            if (alasTeks.isNotEmpty() && tinggiTeks.isNotEmpty()) {
                val alas = alasTeks.toDouble()
                val tinggi = tinggiTeks.toDouble()

                val luas = 0.5 * alas * tinggi
                teksHasil.text = "Luas Segitiga: $luas"

                Log.d("Praktikum", "Berhasil hitung segitiga: $luas")
            } else {
                Toast.makeText(this, "Alas dan Tinggi harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        // 5. KETIKA TOMBOL KUBUS DIKLIK
        btnKubus.setOnClickListener {
            val sisiTeks = inputSisi.text.toString()

            if (sisiTeks.isNotEmpty()) {
                val sisi = sisiTeks.toDouble()

                val volume = sisi * sisi * sisi
                teksHasil.text = "Volume Kubus: $volume"

                Log.d("Praktikum", "Berhasil hitung kubus: $volume")
            } else {
                Toast.makeText(this, "Sisi harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}