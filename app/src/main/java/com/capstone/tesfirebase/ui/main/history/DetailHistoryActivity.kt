package com.capstone.tesfirebase.ui.main.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.capstone.tesfirebase.R
import com.capstone.tesfirebase.databinding.ActivityDetailHistoryBinding

class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val imageUrl = intent.getStringExtra("imageUrl")
        val prediction = intent.getStringExtra("prediction")
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            Glide.with(this@DetailHistoryActivity)
                .load(imageUrl)
                .into(ivImagebuah)
            tvPrediction.text = prediction
            if (prediction == "ripe") {
                tvFruitDescription.text = getString(R.string.desc_buah_matang)
            } else if (prediction == "overripe"){
                tvFruitDescription.text = getString(R.string.desc_buah_busuk)
            } else {
                tvFruitDescription.text = getString(R.string.desc_buah_belum_matang)
            }
        }
    }
}