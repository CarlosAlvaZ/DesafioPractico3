package com.dsa.multimediaappdsa

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dsa.multimediaappdsa.databinding.ActivityCamaraBinding
import android.Manifest
import android.graphics.Bitmap

class CamaraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCamaraBinding

    private val TAG = "INFORMACION"
    private val CAMERA_REQUEST_CODE = 0
    private val STORAGE_REQUEST_CODE = 0
    private val REQUEST_IMAGE_CAPTURE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCamaraBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnTomarFoto.isEnabled = false

        binding.txtMensajeCamara.text = getString(R.string.mensajepermisos)

        binding.btnTomarFoto.setOnClickListener {
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i, 0)
        }

        binding.btnPermisoCamara.setOnClickListener {
            this.checkCameraPermission()
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "Solicitud de permiso")
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.CAMERA
                )
            ) {
                Log.d(TAG, "Debe de dirigirse a ajustes del telefono")
                binding.txtMensajeCamara.text = "Debe de dirigirse a ajustes del telefono"
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE
                )
            }
        } else {
            Log.d(TAG, "El permiso de la camara ha sido concedido")
            binding.btnTomarFoto.isEnabled = true
            binding.txtMensajeCamara.text = "El permiso de la camara ha sido concedido"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imgFoto.setImageBitmap(imageBitmap)
        }
    }
}