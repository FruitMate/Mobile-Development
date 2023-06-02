package com.capstone.tesfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.tesfirebase.databinding.ActivityInformasiBuahBinding

class FruitInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInformasiBuahBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformasiBuahBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}