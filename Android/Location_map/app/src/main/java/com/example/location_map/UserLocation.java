package com.example.location_map;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class UserLocation extends Service implements LocationListener {


    Context context;

    boolean isGPSAvaliable = false;
    // to check network
    boolean isNetworkAvaliable = false;
    // to check GPS
    boolean isLocationAvaliable = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    double latitudeNetwork; // latitude
    double longitudeNetwork; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 200 * 10 * 1; // 2 seconds
    // Declaring a Location Manager
    protected LocationManager locationManager;

    public UserLocation(Context context) {


        this.context = context;
        getLocation();
    }

//    private void showMessage(String text){
//
//        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
//
//    }

    @SuppressLint("MissingPermission")
    private Location getLocation() {

        try {

            locationManager = (LocationManager) context
                    .getSystemService(LOCATION_SERVICE);

            // check if gps is working
            isGPSAvaliable = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // check if network is working
            isNetworkAvaliable = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSAvaliable && !isNetworkAvaliable ){


                // showMessage("Network and GPS are not working!");

            }else {

                this.isLocationAvaliable = true;
                //getting location from network
                if (isNetworkAvaliable) {

                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    // this.showMessage("Network works!");

                    if (this.locationManager != null) {

                        this.location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
//                            this.latitude = location.getLatitude();
//                            this.longitude = location.getLongitude();

                            this.latitudeNetwork = location.getLatitude();
                            this.longitudeNetwork = location.getLongitude();
                        }

                    }

                }else {

                    if (isGPSAvaliable){

                        if(location == null){

                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            if (location != null){

                                this.latitude = location.getLatitude();
                                this.longitude = location.getLongitude();
                            }
                        }
                    }

                }
            }




        }catch (Exception e){

            e.printStackTrace();
        }

        return location;
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation() {
        return this.isLocationAvaliable;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
