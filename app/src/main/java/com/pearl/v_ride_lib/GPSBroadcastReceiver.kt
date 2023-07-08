package com.pearl.v_ride_lib

import android.app.Activity
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import java.lang.ref.WeakReference
import kotlin.system.exitProcess


class GPSBroadcastReceiver : BroadcastReceiver() {

    private var networkDisconnectedDialog: AlertDialog? = null



    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val cm =context. getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo = cm!!.activeNetworkInfo

        when (intent?.action) {
            LocationManager.PROVIDERS_CHANGED_ACTION -> {

                val isNetworkEnabled =
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                if (isGpsEnabled || isNetworkEnabled) {
                    // GPS or network is enabled
                    // You can perform any required actions here
                    Log.d("GPSBroadcastReceiver", "GPS or network enabled")

               /*     if (activeNetworkInfo!!.isConnected) {
                        dismissNetworkDisconnectedDialog()
                    }else{
                        showNetworkDisconnectedDialog(context)
                    }*/
                } else {
                    // GPS and network are disabled
                    // You can perform any required actions here
                    Log.d("GPSBroadcastReceiver", "GPS and network disabled")
                    showNetworkDisconnectedDialog(context)
                    Toast.makeText(context, "Please Turn ON your location", Toast.LENGTH_SHORT)
                        .show()

                }
            }
            ConnectivityManager.CONNECTIVITY_ACTION -> {

                if (activeNetworkInfo != null && activeNetworkInfo!!.isConnected) {
                    // Network is connected
                    // You can perform any required actions here
                    Log.d("GPSBroadcastReceiver", "Network connected")
                    if (isGpsEnabled) {
                        dismissNetworkDisconnectedDialog()
                    }else{
                        showNetworkDisconnectedDialog(context)
                    }
                } else {
                    // Network is disconnected
                    // You can perform any required actions here
                    Log.d("GPSBroadcastReceiver", "Network disconnected")
                    showNetworkDisconnectedDialog(context)

                }
            }
        }
    }

    /*private fun showNetworkDisconnectedDialog(context: Context?) {

        if (networkDisconnectedDialog == null) {
            val alertDialogBuilder = AlertDialog.Builder(context ?: return)
            alertDialogBuilder.setTitle("No Internet Conneciton")
            alertDialogBuilder.setPositiveButton("Try Again") { dialog, _ ->
                val intent = Intent(context, context::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
            alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
                (context as? Activity)?.finishAffinity()
                System.exit(0)
            }
            alertDialogBuilder.setCancelable(false)
            networkDisconnectedDialog = alertDialogBuilder.create()
        }
        networkDisconnectedDialog?.show()
    }*/


    /*fun showNetworkDisconnectedDialog(context: Context?) {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val weakContext = WeakReference(context)

        if (!isGpsEnabled || networkInfo == null || !networkInfo.isConnected) {
            // Network is disconnected
            if (networkDisconnectedDialog == null) {
                val alertDialogBuilder = AlertDialog.Builder(weakContext.get() ?: return)
                alertDialogBuilder.setTitle("Check your Internet Connection or your GPS is Disabled")
                alertDialogBuilder.setPositiveButton("Try Again") { dialog, _ ->
                    val intent = Intent(weakContext.get(), weakContext.get()!!::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    weakContext.get()?.startActivity(intent)
//                    showNetworkDisconnectedDialog(context)
                }
                alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                    (weakContext.get() as? Activity)?.finishAffinity()
                    exitProcess(0)
                }
                alertDialogBuilder.setCancelable(false)
                networkDisconnectedDialog = alertDialogBuilder.create()
            }
            networkDisconnectedDialog?.show()
        }
    }*/
    private fun showNetworkDisconnectedDialog(context: Context?) {
        if (context is Activity && !context.isFinishing) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            if (!isGpsEnabled || networkInfo == null || !networkInfo.isConnected) {
                // Network is disconnected
                if (networkDisconnectedDialog == null) {
                    val alertDialogBuilder = AlertDialog.Builder(context)
                    alertDialogBuilder.setTitle("Check your Internet Connection or your GPS is Disabled")
                    alertDialogBuilder.setPositiveButton("Try Again") { dialog, _ ->
                        val intent = Intent(context, context::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    }
                    alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
                        dialog.cancel()
                        context.finishAffinity()
                        exitProcess(0)
                    }
                    alertDialogBuilder.setCancelable(false)
                    networkDisconnectedDialog = alertDialogBuilder.create()
                }
                networkDisconnectedDialog?.show()
            }
        }
    }


    private fun dismissNetworkDisconnectedDialog() {
        networkDisconnectedDialog?.dismiss()
        networkDisconnectedDialog = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

}

/*class GPSBroadcastReceiver: BroadcastReceiver() {
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
}*/


