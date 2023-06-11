package com.capstone.tesfirebase.ui.main.history

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.capstone.tesfirebase.data.repository.HistoryItem
import com.capstone.tesfirebase.data.repository.HistoryResponse
import com.capstone.tesfirebase.data.repository.News
import com.capstone.tesfirebase.databinding.ItemHistoryBinding
import com.capstone.tesfirebase.databinding.ItemNewsBinding

class HistoryAdapter(private val listHistory: ArrayList<HistoryItem>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewModel>() {

    class HistoryViewModel(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewModel {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewModel(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewModel, position: Int) {
        val currentItem = listHistory[position]

        // Bind data to the view holder's views
        holder.binding.tvCreated.text = currentItem.timestamp

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(currentItem.image_url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Handle image loading failed
                    holder.binding.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Image has been successfully loaded
                    holder.binding.progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(holder.binding.ivImagestory)
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }
}