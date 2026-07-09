package com.example.rby_wwe.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rby_wwe.AuthActivity
import com.example.rby_wwe.Home.berita.NewsAdapter
import com.example.rby_wwe.Home.berita.NewsApiService
import com.example.rby_wwe.Home.berita.NewsResponse
import com.example.rby_wwe.Home.pertemuan_2.HitungActivity
import com.example.rby_wwe.Home.pertemuan_4.halaman2Activity
import com.example.rby_wwe.Home.pertemuan_4.halaman3Activity
import com.example.rby_wwe.Home.pertemuan_6.webActivity
import com.example.rby_wwe.Home.pertemuan_10.TenthActivity
import com.example.rby_wwe.Home.pertemuan_13.ThirteenthActivity
import com.example.rby_wwe.Note.NotesActivity
import com.example.rby_wwe.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsAdapter: NewsAdapter

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

        setupRecyclerView()
        fetchNews()

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val chip = group.findViewById<Chip>(checkedIds[0])
                val filter = chip.text.toString()

                when (filter) {
                    "Semua" -> {
                        binding.layoutMenuUtama.visibility = View.VISIBLE
                        binding.layoutBerita.visibility = View.VISIBLE
                    }
                    "Layanan" -> {
                        binding.layoutMenuUtama.visibility = View.VISIBLE
                        binding.layoutBerita.visibility = View.GONE
                    }
                    "Berita" -> {
                        binding.layoutMenuUtama.visibility = View.GONE
                        binding.layoutBerita.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.layoutMenuUtama.visibility = View.GONE
                        binding.layoutBerita.visibility = View.GONE
                    }
                }
            }
        }

        // Setup click listeners for menu items
        binding.btnHitung.setOnClickListener { startActivity(Intent(requireContext(), HitungActivity::class.java)) }
        binding.btnHalaman2.setOnClickListener { startActivity(Intent(requireContext(), halaman2Activity::class.java)) }
        binding.btnHalaman3.setOnClickListener { startActivity(Intent(requireContext(), halaman3Activity::class.java)) }
        binding.btnPertemuan10.setOnClickListener { startActivity(Intent(requireContext(), TenthActivity::class.java)) }

        binding.btnPertemuan13.setOnClickListener {
            val intent = Intent(requireContext(), ThirteenthActivity::class.java)
            intent.putExtra("TARGET_TAB", 2)
            startActivity(intent)
        }

        binding.btnWeb.setOnClickListener { startActivity(Intent(requireContext(), webActivity::class.java)) }
        binding.btnNotes.setOnClickListener { startActivity(Intent(requireContext(), NotesActivity::class.java)) }
        binding.btnLogout.setOnClickListener { tampilkanDialogLogout() }
    }

    private fun setupRecyclerView() {
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.isNestedScrollingEnabled = false
    }

    private fun fetchNews() {
        binding.pbNews.visibility = View.VISIBLE

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.rss2json.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val service = retrofit.create(NewsApiService::class.java)
        service.getNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (!isAdded) return
                binding.pbNews.visibility = View.GONE
                if (response.isSuccessful) {
                    val posts = response.body()?.posts
                    if (!posts.isNullOrEmpty()) {
                        newsAdapter = NewsAdapter(posts)
                        binding.rvNews.adapter = newsAdapter
                    } else {
                        Log.e("API_ERROR", "Data posts kosong")
                    }
                } else {
                    val errorMsg = "Gagal: ${response.code()} ${response.message()}"
                    Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show()
                    Log.e("API_ERROR", errorMsg)
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                if (!isAdded) return
                binding.pbNews.visibility = View.GONE
                Toast.makeText(requireContext(), "Error Koneksi: ${t.message}", Toast.LENGTH_LONG).show()
                Log.e("API_ERROR", t.message ?: "Unknown Error")
            }
        })
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
            .setNegativeButton("Tidak") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}