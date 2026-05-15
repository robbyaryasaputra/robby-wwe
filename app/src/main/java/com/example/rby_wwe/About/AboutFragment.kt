package com.example.rby_wwe.About

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.rby_wwe.databinding.FragmentAboutBinding
import com.google.android.material.chip.Chip

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Logika ChipGroup
        binding.cgAbout.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val chip = group.findViewById<Chip>(checkedIds[0])
                Toast.makeText(requireContext(), "Kategori: ${chip.text}", Toast.LENGTH_SHORT).show()
            }
        }

        // ListView dengan ArrayAdapter
        val infoList = arrayOf(
            "Kebijakan Privasi",
            "Tentang Bina Desa",
            "Laporan Tahunan Desa",
            "Rencana Pembangunan",
            "Kontak Darurat"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            infoList
        )

        binding.lvPublicInfo.adapter = adapter

        binding.lvPublicInfo.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(requireContext(), "Membuka: ${infoList[position]}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
