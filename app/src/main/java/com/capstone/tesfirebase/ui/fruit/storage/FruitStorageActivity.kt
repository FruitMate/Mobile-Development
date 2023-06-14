package com.capstone.tesfirebase.ui.fruit.storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.tesfirebase.databinding.ActivityFruitStorageBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class FruitStorageActivity : AppCompatActivity() {
    private lateinit var player: ExoPlayer
    companion object {
        const val URL_VIDEO = "https://drive.google.com/uc?export=download&id=1HpVGG9fBAnQuKHWWh4ebOS1e_LLtnSpl"
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

        player = ExoPlayer.Builder(this).build()
        viewBinding.playerView.player = player
        val mediaItem = MediaItem.fromUri(URL_VIDEO)
        player.setMediaItem(mediaItem)
        player.prepare()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }
}