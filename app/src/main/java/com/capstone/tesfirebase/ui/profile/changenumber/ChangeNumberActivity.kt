package com.capstone.tesfirebase.ui.profile.changenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.tesfirebase.R
import com.capstone.tesfirebase.databinding.ActivityChangeEmailBinding
import com.capstone.tesfirebase.databinding.ActivityChangeNumberBinding

class ChangeNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}