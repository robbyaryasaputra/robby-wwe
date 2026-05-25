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
import com.google.android.material.chip.Chip

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

        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val userName = requireActivity().intent.getStringExtra("USER_NAME") ?: sharedPref.getString("username", "Robby")
        binding.tvWelcome.text = "Halo, $userName"

        // Logika ChipGroup untuk Filter Menu
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val chip = group.findViewById<Chip>(checkedIds[0])
                val filter = chip.text.toString()
                
                when (filter) {
                    "Semua" -> {
                        binding.btnHitung.visibility = View.VISIBLE
                        binding.btnHalaman2.visibility = View.VISIBLE
                        binding.btnHalaman3.visibility = View.VISIBLE
                        binding.btnWeb.visibility = View.VISIBLE
                    }
                    "Layanan" -> {
                        binding.btnHitung.visibility = View.VISIBLE
                        binding.btnWeb.visibility = View.VISIBLE
                        binding.btnHalaman2.visibility = View.GONE
                        binding.btnHalaman3.visibility = View.GONE
                    }
                    "Berita" -> {
                        binding.btnHalaman2.visibility = View.VISIBLE
                        binding.btnHitung.visibility = View.GONE
                        binding.btnHalaman3.visibility = View.GONE
                        binding.btnWeb.visibility = View.GONE
                    }
                    "Kegiatan" -> {
                        binding.btnHalaman3.visibility = View.VISIBLE
                        binding.btnHitung.visibility = View.GONE
                        binding.btnHalaman2.visibility = View.GONE
                        binding.btnWeb.visibility = View.GONE
                    }
                }
                Snackbar.make(binding.root, "Filter: $filter", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnHitung.setOnClickListener {
            startActivity(Intent(requireContext(), HitungActivity::class.java))
        }

        binding.btnHalaman2.setOnClickListener {
            val intent = Intent(requireContext(), halaman2Activity::class.java).apply {
                putExtra("TITLE", "Halaman 2")
                putExtra("DESC", "Menyajikan harmoni visual Bina Desa.")
            }
            startActivity(intent)
        }

        binding.btnHalaman3.setOnClickListener {
            val intent = Intent(requireContext(), halaman3Activity::class.java).apply {
                putExtra("TITLE", "Halaman 3")
                putExtra("DESC", "Profil Estetik Bina Desa.")
            }
            startActivity(intent)
        }

        binding.btnWeb.setOnClickListener {
            startActivity(Intent(requireContext(), webActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            tampilkanDialogLogout()
        }
    }

    private fun tampilkanDialogLogout() {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah kamu yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                sharedPref.edit { clear() }
                val intent = Intent(requireContext(), AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
