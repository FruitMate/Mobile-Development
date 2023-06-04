package com.capstone.tesfirebase.ui.fruit.storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.tesfirebase.databinding.ActivityFruitStorageBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class FruitStorageActivity : AppCompatActivity() {
    companion object {
        const val URL_VIDEO = "https://github.com/FruitMate/Mobile-Development/raw/branch1/asset/Tips%20Menyimpan%20Apel%20Agar%20Tahan%20Lama%20%26%20Tidak%20Cokelat.mp4"
    }
    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityFruitStorageBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        supportActionBar?.hide()

        viewBinding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }

        val player = ExoPlayer.Builder(this).build()
        viewBinding.playerView.player = player
        val mediaItem = MediaItem.fromUri(URL_VIDEO)
        player.setMediaItem(mediaItem)
        player.prepare()
    }
}