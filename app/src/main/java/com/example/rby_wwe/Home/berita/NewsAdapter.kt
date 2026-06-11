package com.example.rby_wwe.Home.berita

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rby_wwe.R
import com.example.rby_wwe.databinding.ItemNewsBinding

class NewsAdapter(private val listNews: List<NewsPost>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsPost) {
            binding.tvNewsTitle.text = news.title
            binding.tvNewsDate.text = news.pubDate
            binding.tvNewsDesc.text = news.description
            
            // Logika lebih kuat untuk mengambil URL Gambar dari RSS2JSON
            val imageUrl = when {
                !news.thumbnail.isNullOrEmpty() -> news.thumbnail
                news.enclosure?.link != null -> news.enclosure.link
                news.enclosure?.thumbnail != null -> news.enclosure.thumbnail
                else -> ""
            }

            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .centerCrop()
                .into(binding.ivNews)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount(): Int = listNews.size
}
