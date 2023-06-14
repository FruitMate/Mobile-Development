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

        fun transformPrediction(prediction: String?): String? {
            return when (prediction) {
                "ripe" -> "Matang"
                "overripe" -> "Busuk"
                "unripe" -> "Belum Matang"
                else -> prediction // Mengembalikan hasil prediksi asli jika tidak ada transformasi yang cocok
            }
        }

        currentPhotoPath?.let { path ->
            val file = File(path)
            if (file.exists()) {
                binding.ivScanImage.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
        binding.classificationResult.text = transformPrediction(prediction)

        if (prediction == "Matang") {
            binding.textView.text = getString(R.string.desc_buah_matang)
        }

        else if (prediction == "Busuk"){
            binding.textView.text = getString(R.string.desc_buah_busuk)
        }

        else {
            binding.textView.text = getString(R.string.desc_buah_belum_matang)
        }
    }
}