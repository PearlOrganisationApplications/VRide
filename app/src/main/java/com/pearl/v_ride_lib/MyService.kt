package com.pearl.v_ride_lib

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit
import kotlin.time.DurationUnit

class MyService : Service() {
    private lateinit var prefManager: PrefManager
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate() {
        super.onCreate()
        prefManager = PrefManager(applicationContext)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        createLocationRequest()
        createLocationCallback()
        startLocationUpdates()

        Log.d("onCreateService"," onCreate")

    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 5 * 60 * 1000 // Update interval of 5 minutes
            fastestInterval = 5 * 60 * 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                p0.let { result ->
                    val location = result.lastLocation
                    handleLocationUpdate(location!!)
                }
            }
        }
    }

/*override fun onLocationResult(locationResult: LocationResult?) {
    locationResult?.let { result ->
        val location = result.lastLocation
        handleLocationUpdate(location!!)
    }*/

    private fun handleLocationUpdate(location: Location) {
        prefManager.setlatitude(location.latitude)
        prefManager.setlongitude(location.longitude)
        Log.d("handleLocationUpdate",location.latitude.toString()+" "+location.longitude.toString())
        val lat =prefManager.getlatitude().toDouble()
        val lon =  prefManager.getlongitude().toDouble()
        /*val lat = location.latitude
        val lon = location.longitude*/

        sendLocationToServer(lat, lon)
    }

    @SuppressLint("InvalidPeriodicWorkRequestInterval")
    private fun sendLocationToServer(lat: Double, lon: Double) {
        val data = Data.Builder()
            .putDouble("latitude", lat)
            .putDouble("longitude", lon)
            .build()

        val uploadWorkRequest = PeriodicWorkRequest.Builder(LocationWorker::class.java,5,TimeUnit.MINUTES)
            .setInputData(data)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                "LocationUploadWorker",
                ExistingPeriodicWorkPolicy.REPLACE,
                uploadWorkRequest
            )
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}