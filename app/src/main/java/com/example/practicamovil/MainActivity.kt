package com.example.practicamovil

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.practicamovil.databinding.ActivityMainBinding
import android.Manifest
import android.content.Intent
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val CAMERA_PERMISSION = 100
    private val CONTACTS_PERMISSION = 101
    private val CAMERA_STORAGE_PERMISSION = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener {

            val cameraPermissionGranted =
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            val storagePermissionGranted =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            val storageWritePermissionGranted =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

            val permissionsToRequest = mutableListOf<String>()

            if (!cameraPermissionGranted) {
                permissionsToRequest.add(Manifest.permission.CAMERA)
            }
            if (!storagePermissionGranted) {
                permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (!storageWritePermissionGranted) {
                permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (permissionsToRequest.isNotEmpty()) {

                ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), CAMERA_PERMISSION)
            } else {
                // Both permissions are already granted, you can proceed to pick an image
                acceactivitycamera()
            }

        }

        binding.button2.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    CONTACTS_PERMISSION
                )


            } else {
                accessContacts()

            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    // Camera and storage permissions granted, handle your action here
                    acceactivitycamera()
                } else {
                    // Handle the case where permissions are not granted
                    acceactivitycamera()
                    Toast.makeText(this, "Camera and storage permissions are required", Toast.LENGTH_LONG).show()
                }
            }
            CONTACTS_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    accessContacts()
                } else {
                    accessContacts()
                    Toast.makeText(this, "El permiso de contactos no fue concedido", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun accessContacts(){
        val pasarcontactos = Intent(this,Contactos::class.java)
        startActivity(pasarcontactos)
    }

    fun acceactivitycamera(){
        val pasarcaemra= Intent(this,CameraescogerActivity::class.java)
        startActivity(pasarcaemra)
    }
}