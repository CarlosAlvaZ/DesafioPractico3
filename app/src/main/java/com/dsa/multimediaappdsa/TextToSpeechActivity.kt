package com.dsa.multimediaappdsa

import android.os.Bundle
import android.os.Environment
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dsa.multimediaappdsa.databinding.ActivityTextToSpeechBinding
import org.w3c.dom.Text
import java.util.Locale

class TextToSpeechActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTextToSpeechBinding

    private lateinit var tts: TextToSpeech
    private var numarch = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTextToSpeechBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        tts = TextToSpeech(this@TextToSpeechActivity, onInit)
        binding.btnText2SpeechPlay.setOnClickListener(onClick)
        binding.btnText2SpeechSave.setOnClickListener(onClick)
    }

    private var onInit = OnInitListener { status ->
        if (TextToSpeech.SUCCESS == status) {
            tts.language = Locale("spa", "ESP")
        } else {
            Toast.makeText(applicationContext, "TTS no disponible", Toast.LENGTH_SHORT).show()
        }
    }

    private val onClick = View.OnClickListener {
        when(it.id) {
            R.id.btnText2SpeechPlay -> {
                tts.speak(binding.edtText2Speech.text.toString(), TextToSpeech.QUEUE_ADD, null)
            }
            R.id.btnText2SpeechSave -> {
                tts.speak(binding.edtText2Speech.text.toString(), TextToSpeech.QUEUE_ADD, null)
                val myHashRender = HashMap<String, String>()
                val Texto_tts = binding.edtText2Speech.text.toString()

                numarch = numarch + 1
                val destFileName: String = Environment.getExternalStorageDirectory().absolutePath + "/Download/tts" + numarch + ".wav"
                myHashRender[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = Texto_tts

                tts.synthesizeToFile(Texto_tts, myHashRender, destFileName)
            }
        }
    }

    override fun onDestroy() {
        tts.shutdown()
        super.onDestroy()
    }
}