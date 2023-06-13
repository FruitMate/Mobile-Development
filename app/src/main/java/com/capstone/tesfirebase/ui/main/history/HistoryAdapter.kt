package com.capstone.tesfirebase.ui.main.history

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.capstone.tesfirebase.data.response.HistoryResponse
import com.capstone.tesfirebase.databinding.ItemHistoryBinding

class HistoryAdapter(private val listHistory: ArrayList<HistoryResponse>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewModel>() {

    class HistoryViewModel(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewModel {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewModel(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewModel, position: Int) {
        val currentItem = listHistory[position]

        // Bind data to the view holder's views
        holder.binding.tvCreated.text = currentItem.timestamp
        holder.binding.tvClassification.text = currentItem.classification_result


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