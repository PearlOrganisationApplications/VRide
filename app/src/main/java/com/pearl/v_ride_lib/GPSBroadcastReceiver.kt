package com.pearl.v_ride_lib

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.widget.Toast

class GPSBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        try {
            var lp=p0!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (lp.isProviderEnabled(LocationManager.GPS_PROVIDER)
            ) {
                //isGPSEnabled = true;
            } else {
                Toast.makeText(p0,"Please Turn ON your location", Toast.LENGTH_SHORT).show()
                //isGPSEnabled = false;
            }
        } catch (ex: Exception) {
        }
    }
}