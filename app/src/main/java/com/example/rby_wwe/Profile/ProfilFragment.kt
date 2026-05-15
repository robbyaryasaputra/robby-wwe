package com.example.rby_wwe.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
import com.example.rby_wwe.R
import com.example.rby_wwe.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
    }

    private fun setupListView() {
        val menuItems = arrayOf("Kebijakan Privasi", "Syarat & Ketentuan", "Bantuan", "Tentang Aplikasi")
        val menuIcons = arrayOf(R.drawable.ic_lock, R.drawable.ic_category, R.drawable.ic_person_search, R.drawable.ic_group)

        val list = ArrayList<HashMap<String, Any>>()
        for (i in menuItems.indices) {
            val map = HashMap<String, Any>()
            map["title"] = menuItems[i]
            map["icon"] = menuIcons[i]
            list.add(map)
        }

        val from = arrayOf("title", "icon")
        val to = intArrayOf(R.id.itemTitle, R.id.itemIcon)

        val adapter = SimpleAdapter(requireContext(), list, R.layout.item_list_simple, from, to)
        binding.lvSettings.adapter = adapter

        binding.lvSettings.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(requireContext(), "Membuka ${menuItems[position]}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
