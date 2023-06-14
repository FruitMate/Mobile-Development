package com.capstone.tesfirebase.ui.main.history

import android.annotation.SuppressLint
import android.content.Intent
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
import com.capstone.tesfirebase.data.response.HistoryItem
import com.capstone.tesfirebase.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private var listHistory: List<HistoryItem>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewModel>() {

    class HistoryViewModel(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewModel {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewModel(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewModel, position: Int) {
        val currentItem = listHistory[position]

        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = currentItem.timestamp?.let { inputFormat.parse(it) }
        val formattedDate = parsedDate?.let { outputFormat.format(it) }

        // Bind data to the view holder's views
        holder.binding.tvCreated.text = formattedDate
        holder.binding.tvClassification.text = transformPrediction(currentItem.classification_result)


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
            .into(holder.binding.ivImagehistory)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailHistoryActivity::class.java)
            intent.putExtra("imageUrl", currentItem.image_url)
            intent.putExtra("prediction", transformPrediction(currentItem.classification_result))
            holder.itemView.context.startActivity(intent)
        }
    }

    fun transformPrediction(prediction: String?): String? {
        return when (prediction) {
            "ripe" -> "Matang"
            "overripe" -> "Busuk"
            "unripe" -> "Belum Matang"
            else -> prediction // Mengembalikan hasil prediksi asli jika tidak ada transformasi yang cocok
        }
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<HistoryItem>) {
        listHistory = newData
        notifyDataSetChanged()
    }
}