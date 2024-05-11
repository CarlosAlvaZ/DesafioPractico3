package com.dsa.multimediaappdsa

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.MediaController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dsa.multimediaappdsa.databinding.ActivityVideoBinding
import java.io.File
import android.Manifest

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding

    private lateinit var mediacontrol: MediaController
    private val STORAGE_REQUEST_CODE = 0
    private val TAG = "INFORMACION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVideoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.idCargarVideo.isEnabled = false

        binding.idCargarVideo.setOnClickListener {
            binding.textVideo.text = getString(R.string.mensajevideo)

            var f = File(
                Environment.getExternalStorageDirectory().absolutePath + "/Download", "video.3gp"
            )

            if (f.exists()) {
                val uri: Uri = Uri.fromFile(f)
                binding.video.setVideoURI(uri)
                mediacontrol = MediaController(this)
                binding.video.setMediaController(mediacontrol)
                mediacontrol.show()
            } else {
                binding.textVideo.text = "Video no encontrado"
            }

        }

        binding.idSolicitarPermisos.setOnClickListener {
            this.checkStoragePermission()
        }
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("Informacion", "Solicitud de permiso storage")
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                Log.d("Informacion", "Debe de dirigirse a ajustes del telefono")
                binding.textVideo.text = "Debe de dirigirse a ajustes del telefono"
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_REQUEST_CODE
                )
            }
        } else {
            Log.d("Informacion", "Se ha concedido permiso al almacenamiento")
            binding.textVideo.text = "Se ha concedido permiso al almacenamiento"
            binding.idCargarVideo.isEnabled = true
        }
    }
}