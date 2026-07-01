package com.example.rby_wwe.About

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.rby_wwe.BaseActivity
import com.example.rby_wwe.databinding.FragmentAboutBinding
import com.example.rby_wwe.utils.NotificationHelper
import com.example.rby_wwe.utils.PermissionHelper
import com.google.android.material.chip.Chip

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    // Launcher untuk meminta izin (Android 13+)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(requireContext(), "Izin notifikasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cek dan minta izin notifikasi saat halaman dibuka
        if (PermissionHelper.isNotificationPermissionRequired()) {
            if (!PermissionHelper.hasPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)) {
                PermissionHelper.requestPermission(requestPermissionLauncher, Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        // Setup ListView
        val infoList = arrayOf("Kebijakan Privasi", "Tentang Bina Desa", "Kontak Darurat")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, infoList)
        binding.lvPublicInfo.adapter = adapter

        // Klik Item -> Muncul Notifikasi Heads-up -> Ke Home (BaseActivity)
        binding.lvPublicInfo.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(requireContext(), BaseActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            
            NotificationHelper.showNotification(
                requireContext(),
                "Informasi Desa",
                "Membuka Dashboard: ${infoList[position]}",
                intent
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
