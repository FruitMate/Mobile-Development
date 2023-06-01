package com.capstone.tesfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.tesfirebase.databinding.ActivityPenyimpananBuahBinding

class PenyimpananBuahActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPenyimpananBuahBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPenyimpananBuahBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}