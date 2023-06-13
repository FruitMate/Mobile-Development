package com.capstone.tesfirebase.ui.scanresult

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tesfirebase.R
import com.capstone.tesfirebase.databinding.ActivityScanResultBinding
import java.io.File

class ScanResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.btnBack.setOnClickListener{
            onBackPressed()
        }

        val currentPhotoPath = intent.getStringExtra("currentPhotoPath")
        val prediction = intent.getStringExtra("prediction")

        currentPhotoPath?.let { path ->
            val file = File(path)
            if (file.exists()) {
                binding.ivScanImage.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
        binding.classificationResult.text = prediction

        if (prediction == "ripe") {
            binding.textView.text = getString(R.string.desc_buah_matang)
        }

        else if (prediction == "overripe"){
            binding.textView.text = getString(R.string.desc_buah_busuk)
        }

        else {binding.textView.text = getString(R.string.desc_buah_belum_matang)

        }
    }
}