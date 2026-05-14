package com.example.rby_wwe.Home.pertemuan_4

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rby_wwe.AuthActivity
import com.example.rby_wwe.databinding.ActivityDashboardBinding
import com.example.rby_wwe.Home.pertemuan_2.HitungActivity
import com.example.rby_wwe.Home.pertemuan_6.webActivity
import com.google.android.material.snackbar.Snackbar

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi View Binding
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Menerima data nama user dari intent atau SharedPreferences
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val userName = intent.getStringExtra("USER_NAME") ?: sharedPref.getString("username", "Robby")
        binding.tvWelcome.text = "Halo, $userName"

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Tombol Buka HitungActivity
        binding.btnHitung.setOnClickListener {
            val intent = Intent(this, HitungActivity::class.java)
            startActivity(intent)
        }

        // 2. Tombol Buka Halaman 2 + Kirim Data (Title & Desc)
        binding.btnHalaman2.setOnClickListener {
            val intent = Intent(this, halaman2Activity::class.java)
            intent.putExtra("TITLE", "Halaman 2")
            intent.putExtra("DESC", "Menyajikan harmoni visual yang memukau. Halaman ini memadukan latar belakang imersif dengan translucent card, menciptakan pengalaman membaca yang estetis dan nyaman.")
            startActivity(intent)
        }

        // 3. Tombol Buka Halaman 3 + Kirim Data (Title & Desc)
        binding.btnHalaman3.setOnClickListener {
            val intent = Intent(this, halaman3Activity::class.java)
            intent.putExtra("TITLE", "Halaman 3")
            intent.putExtra("DESC", "Selamat datang di Profil Anda! Halaman ini didesain secara khusus menampilkan perpaduan tata letak modern dan kartu transparan bergaya estetis.")
            startActivity(intent)
        }

        // 4. Tombol Web Bina Desa
        binding.btnWeb.setOnClickListener {
            val intent = Intent(this, webActivity::class.java)
            startActivity(intent)
        }

        // 5. Tombol Logout dengan AlertDialog & SnackBar
        binding.btnLogout.setOnClickListener {
            tampilkanDialogLogout()
        }
    }

    private fun tampilkanDialogLogout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Logout")
        builder.setMessage("Apakah kamu yakin ingin keluar dari aplikasi?")

        // Jika pilih Ya
        builder.setPositiveButton("Ya") { dialog, which ->
            // Hapus session di SharedPreferences
            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
            sharedPref.edit {
                clear()
            }

            val intent = Intent(this, AuthActivity::class.java)
            // Membersihkan backstack agar user tidak bisa tekan tombol 'back' untuk kembali ke dashboard
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Jika pilih Tidak
        builder.setNegativeButton("Tidak") { dialog, which ->
            // Munculkan SnackBar jika dibatalkan
            Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}
