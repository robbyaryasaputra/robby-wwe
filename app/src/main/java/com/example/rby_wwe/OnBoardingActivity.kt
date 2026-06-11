package com.example.rby_wwe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.rby_wwe.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupOnBoardingItems()

        binding.viewPager.adapter = onBoardingAdapter
        
        // Hubungkan Dots Indicator dengan ViewPager2 menggunakan attachTo
        binding.dotsIndicator.attachTo(binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == onBoardingAdapter.itemCount - 1) {
                    binding.btnStart.visibility = View.VISIBLE
                } else {
                    binding.btnStart.visibility = View.GONE
                }
            }
        })

        binding.btnStart.setOnClickListener {
            val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            sharedPref.edit().putBoolean("finishedOnBoarding", true).apply()
            
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }

    private fun setupOnBoardingItems() {
        val onBoardingItems = listOf(
            OnBoardingItem(
                R.drawable.ic_launcher_foreground, // Ganti dengan image yang sesuai
                "Selamat Datang di RBY WWE",
                "Temukan informasi terbaru seputar dunia gulat profesional di genggaman Anda."
            ),
            OnBoardingItem(
                R.drawable.ic_launcher_foreground,
                "Update Real-Time",
                "Dapatkan berita dan hasil pertandingan paling update setiap harinya."
            ),
            OnBoardingItem(
                R.drawable.ic_launcher_foreground,
                "Komunitas Fans",
                "Bergabung dengan ribuan fans lainnya dan bagikan opini Anda."
            )
        )
        onBoardingAdapter = OnBoardingAdapter(onBoardingItems)
    }

    data class OnBoardingItem(
        val image: Int,
        val title: String,
        val description: String
    )

    inner class OnBoardingAdapter(private val items: List<OnBoardingItem>) :
        RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
            return OnBoardingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_on_boarding,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

        inner class OnBoardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val imageOnBoarding = view.findViewById<ImageView>(R.id.ivOnBoarding)
            private val textTitle = view.findViewById<TextView>(R.id.tvTitle)
            private val textDescription = view.findViewById<TextView>(R.id.tvDescription)

            fun bind(item: OnBoardingItem) {
                imageOnBoarding.setImageResource(item.image)
                textTitle.text = item.title
                textDescription.text = item.description
            }
        }
    }
}
