package com.example.practicamovil

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.practicamovil.databinding.ActivityCameraescogerBinding
import com.example.practicamovil.databinding.ActivityMainBinding
import java.io.File

class CameraescogerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraescogerBinding
    private lateinit var camerapath:Uri
    private val cameraRequest = registerForActivityResult(ActivityResultContracts.TakePicture()
    ) { loadImage(camerapath) }

    private val GalleryRequest = registerForActivityResult(ActivityResultContracts.GetContent()
    ) { loadImage(camerapath) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cameraescoger)

        binding = ActivityCameraescogerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initializeFile()

        binding.button3.setOnClickListener{
            cameraRequest.launch(camerapath)
        }

        binding.button4.setOnClickListener {
            // Open gallery
            GalleryRequest.launch("image/*")
        }

    }

    fun initializeFile(){
        var imagetoload = File(filesDir,"fileFromCamera")
        camerapath = FileProvider.getUriForFile(this, applicationContext.packageName + ".fileprovider", imagetoload)

    }
    fun loadImage(imagepath:Uri?){

        val imagestream = contentResolver.openInputStream(imagepath!!)
        val image = BitmapFactory.decodeStream(imagestream)
        binding.imageView2.setImageBitmap(image)

    }
/*
    private lateinit var binding: ActivityCameraescogerBinding
    private val CAMERA_STORAGE_PERMISSION = 102

    val REQUEST_IMAGE_CAPTURE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cameraescoger)


        binding = ActivityCameraescogerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button3.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_STORAGE_PERMISSION
                )
            } else {
                dispatchTakePictureIntent()
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imageView2.setImageBitmap(imageBitmap)
        }
    }

    */
}