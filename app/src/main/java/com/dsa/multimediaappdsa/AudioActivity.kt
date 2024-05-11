package com.dsa.multimediaappdsa

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsa.multimediaappdsa.databinding.ActivityAudioBinding
import java.io.IOException
import java.lang.IllegalStateException

class AudioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAudioBinding

    private lateinit var Media: MediaPlayer

    val audios = arrayListOf(
        AudioResource("Song 1", R.raw.music),
        AudioResource("Song 2", R.raw.music2),
        AudioResource("Song 3", R.raw.music3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAudioBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.play.setOnClickListener(onClick)
        binding.stop.setOnClickListener(onClick)

        Media = MediaPlayer.create(applicationContext, R.raw.music)

        initRecyclerView()
    }

    private fun onClickListener(resouce: Int) {
        if (Media.isPlaying) {
            Media.stop()
        }
        Media = MediaPlayer.create(applicationContext, resouce)
        Media.start()
    }

    private fun initRecyclerView() {
        binding.audios.layoutManager = LinearLayoutManager(this)
        binding.audios.adapter = AudioAdapter(audios) { onClickListener(it) }
    }

    private val onClick = View.OnClickListener {
        when (it.id) {
            R.id.play -> {
                if (Media.isPlaying) {
                    Media.pause()
                    binding.play.text = "Play"
                } else {
                    Media.start()
                    binding.play.text = "Pause"
                }
            }

            R.id.stop -> {
                Media.stop()
                binding.play.text = "Play"
                try {
                    Media.prepare()
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}