package com.example.practicamovil

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.practicamovil.databinding.ActivityLocationBinding
import com.example.practicamovil.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationActivity : AppCompatActivity() {

    private lateinit var bindingLocation: ActivityLocationBinding
    private lateinit var mFusedLocationClient:FusedLocationProviderClient

    private val LOCATION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)


        bindingLocation = ActivityLocationBinding.inflate(layoutInflater)
        val view = bindingLocation.root
        setContentView(view)

        conseguirlugar()

        bindingLocation.button6.setOnClickListener{
            conseguirlugar()
        }


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                conseguirlugar()

            } else {
                // Permission denied, handle accordingly (e.g., show a message or disable contacts-related features)
                Toast.makeText(this, "El permiso de ubicaion no fue concedido", Toast.LENGTH_LONG).show()
            }
        }

    }
    fun conseguirlugar(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)

        } else {
            mFusedLocationClient= LocationServices.getFusedLocationProviderClient(this)
            mFusedLocationClient.lastLocation.addOnSuccessListener(this) {location->
                Log.i("Location","Onsucceslocation")
                if(location != null){
                    Log.i("Location","latitud"+ location.longitude)
                    bindingLocation.textView13.text = location.longitude.toString()
                    bindingLocation.textView12.text = location.latitude.toString()
                    bindingLocation.textView11.text = location.altitude.toString()
                }

            }


        }

    }

}