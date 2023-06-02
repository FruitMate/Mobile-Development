package com.capstone.tesfirebase.ui.fruit.treecare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.tesfirebase.databinding.ActivityFruitTreecareBinding


class FruitTreeCareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFruitTreecareBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitTreecareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}