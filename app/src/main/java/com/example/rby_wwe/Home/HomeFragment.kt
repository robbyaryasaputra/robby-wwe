package com.example.rby_wwe.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import com.example.rby_wwe.AuthActivity
import com.example.rby_wwe.Home.pertemuan_2.HitungActivity
import com.example.rby_wwe.Home.pertemuan_4.halaman2Activity
import com.example.rby_wwe.Home.pertemuan_4.halaman3Activity
import com.example.rby_wwe.Home.pertemuan_6.webActivity
import com.example.rby_wwe.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menerima data nama user dari intent atau SharedPreferences
        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val userName = requireActivity().intent.getStringExtra("USER_NAME") ?: sharedPref.getString("username", "Robby")
        binding.tvWelcome.text = "Halo, $userName"

        // --- Logika Tombol Dashboard ---

        // 1. Tombol Buka HitungActivity
        binding.btnHitung.setOnClickListener {
            val intent = Intent(requireContext(), HitungActivity::class.java)
            startActivity(intent)
        }

        // 2. Tombol Buka Halaman 2
        binding.btnHalaman2.setOnClickListener {
            val intent = Intent(requireContext(), halaman2Activity::class.java)
            intent.putExtra("TITLE", "Halaman 2")
            intent.putExtra("DESC", "Menyajikan harmoni visual yang memukau. Halaman ini memadukan latar belakang imersif dengan translucent card, menciptakan pengalaman membaca yang estetis dan nyaman.")
            startActivity(intent)
        }

        // 3. Tombol Buka Halaman 3
        binding.btnHalaman3.setOnClickListener {
            val intent = Intent(requireContext(), halaman3Activity::class.java)
            intent.putExtra("TITLE", "Halaman 3")
            intent.putExtra("DESC", "Selamat datang di Profil Anda! Halaman ini didesain secara khusus menampilkan perpaduan tata letak modern dan kartu transparan bergaya estetis.")
            startActivity(intent)
        }

        // 4. Tombol Web Bina Desa
        binding.btnWeb.setOnClickListener {
            val intent = Intent(requireContext(), webActivity::class.java)
            startActivity(intent)
        }

        // 5. Tombol Logout
        binding.btnLogout.setOnClickListener {
            tampilkanDialogLogout()
        }
    }

    private fun tampilkanDialogLogout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Konfirmasi Logout")
        builder.setMessage("Apakah kamu yakin ingin keluar dari aplikasi?")

        builder.setPositiveButton("Ya") { _, _ ->
            // Hapus session
            val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            sharedPref.edit {
                clear()
            }

            val intent = Intent(requireContext(), AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        builder.setNegativeButton("Tidak") { dialog, _ ->
            Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
