package com.capstone.tesfirebase.ui.profile.changeemail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.tesfirebase.R
import com.capstone.tesfirebase.databinding.ActivityChangeEmailBinding
import com.capstone.tesfirebase.databinding.ActivityChangePasswordBinding

class ChangeEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}