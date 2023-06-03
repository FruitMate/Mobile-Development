package com.capstone.tesfirebase.ui.profile.changepassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.tesfirebase.R
import com.capstone.tesfirebase.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}