package com.example.location_map

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_maps.*
import android.R.attr.strokeWidth
import android.R.attr.fillColor
import android.R.attr.radius
import android.graphics.Color
import com.google.android.gms.maps.model.CircleOptions
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
    private lateinit var lastLocation: Location


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
      //  this.drawCircle()


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        when (item.itemId) {
            R.id.user_in -> {

                this.calculateDistance(51.448920, 5.487230 )
                return true
            }
            R.id.user_out -> {

                this.calculateDistance(51.434890,5.479580 )
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true

        // leftPadding, topPadding, rightPadding, bottomPadding
        mMap.setPadding(20, 20, 20, 350)


        setUpMap()
        this.drawCircle()

    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    fun drawCircle(){

        val circleOptions = CircleOptions()
        circleOptions.center(LatLng(51.451550, 5.481820))
        circleOptions.radius(700.0)
        circleOptions.fillColor(Color.BLUE)
        circleOptions.strokeWidth(6f)
        mMap.addCircle(circleOptions)
    }

    fun calculateDistance(lat:Double, long:Double){

        val userLoc =  UserLocation(this)
        val myPlace = LatLng(lat, long)
        mMap.clear()


        val results = FloatArray(1)
        Location.distanceBetween(
            lat, long,
            51.451550, 5.481820,
            results
        )

        if(results[0] < 1000){

            mMap.addMarker(MarkerOptions().position(myPlace).title("Close by Fontys"))

            Toast.makeText(this, "User close to Fontys , less than 1000 meters!", Toast.LENGTH_LONG).show()
            Log.e("TAG", results[0].toString())

        }else{

            mMap.addMarker(MarkerOptions().position(myPlace).title("Far from Fontys"))

            Toast.makeText(this, "User far away from Fontys , more than 1000 meters!", Toast.LENGTH_LONG).show()
            Log.e("TAG", results[0].toString())

        }
    }

}


