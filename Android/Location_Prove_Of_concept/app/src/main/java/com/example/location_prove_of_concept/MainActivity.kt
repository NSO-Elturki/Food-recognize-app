package com.example.location_prove_of_concept

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions,0)



        btn.setOnClickListener {

            this.getLocation()
        }

    }

     fun getLocation(){

         val userLocation = UserLocation(this)


         if (userLocation.canGetLocation()) {
             val  latitude = userLocation.getLatitude()
             val  longitude = userLocation.getLongitude()
             val netWorkLong = userLocation.longitudeNetwork
             val netWorkLat = userLocation.latitudeNetwork
             val gpsTxt = findViewById<TextView>(R.id.gpsTxt)
             val netWorkTxt = findViewById<TextView>(R.id.networkTx)

            gpsTxt.text = " GPS: Lat: $latitude, Lang: $longitude"
            netWorkTxt.text = "Network: Lat: $netWorkLat, Lang: $netWorkLong"

//             Toast.makeText(this, "Lat: $latitude, Lang: $longitude", Toast.LENGTH_LONG ).show()
         }


     }



    }




