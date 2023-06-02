package com.capstone.tesfirebase.ui.fruit.treecare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.tesfirebase.databinding.ActivityPerawatanTanamanBinding

class FruitTreeCareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerawatanTanamanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerawatanTanamanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}