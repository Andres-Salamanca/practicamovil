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
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationActivity : AppCompatActivity() {

    private lateinit var bindingLocation: ActivityLocationBinding
    private lateinit var mFusedLocationClient:FusedLocationProviderClient
    private lateinit var mLocationRquest:LocationRequest
    private lateinit var mLcationCallback: LocationCallback

    private val LOCATION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)


        bindingLocation = ActivityLocationBinding.inflate(layoutInflater)
        val view = bindingLocation.root
        setContentView(view)

        mLocationRquest = createLocationRequest()

        mLcationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                Log.i("LOCATIONes", "location update in the callback $location")
                if (location != null) {
                    bindingLocation.textView13.text = "123"
                    bindingLocation.textView12.text = location.latitude.toString()
                    bindingLocation.textView11.text = location.altitude.toString()
                    Log.i("location", "latitude" +location.latitude)
                }
            }
        }


        startLocationUpdates()

        conseguirlugar()

        bindingLocation.button6.setOnClickListener{
            conseguirlugar()
        }




    }

    private fun createLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.create()
            .setInterval(5000) // 5 seconds
            .setFastestInterval(5000) // 5 seconds
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        return locationRequest
    }
    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            mFusedLocationClient.requestLocationUpdates(mLocationRquest, mLcationCallback, null)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
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