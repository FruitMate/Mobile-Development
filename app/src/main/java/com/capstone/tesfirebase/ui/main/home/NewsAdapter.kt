package com.capstone.tesfirebase.ui.main.home

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.tesfirebase.data.repository.News
import com.capstone.tesfirebase.databinding.ItemNewsBinding

class NewsAdapter(private val listNews: ArrayList<News>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = listNews[position]
        holder.binding.newsImage.setImageResource(news.photo)
        holder.binding.newsTitle.text = news.title

        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

}