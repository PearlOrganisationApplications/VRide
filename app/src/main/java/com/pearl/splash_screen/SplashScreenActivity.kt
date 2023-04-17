package com.pearl.splash_screen

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.android.volley.Response.error
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStates
import com.pearl.test5.R
import com.pearl.v_ride.WelcomeScreen
import com.pearl.v_ride_lib.PrefManager

class SplashScreenActivity : AppCompatActivity() {

    private val REQUEST_CODE = 101
/*    private val REQUEST_CHECK_SETTINGS: Int=101
    private lateinit var locationRequest: LocationRequest
    var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=101

    var prefManager: PrefManager? = null

    var goneToSettings :Boolean = false*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val i = Intent(this@SplashScreenActivity, WelcomeScreen::class.java)
            startActivity(i)
            finish()
        },2000)

/*        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
        }*/
    }


/*    fun grantLocPer() {

        if (isLocationPermissionGranted()) {






            checkLocationService()
        }

        else {
            ActivityCompat.requestPermissions(
                this@SplashScreenActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)

        }
    }



    fun checkLocationService() {

        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY


        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        // builder.setAlwaysShow(true);
        val client = LocationServices.getSettingsClient(this@SplashScreenActivity)
        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener(this@SplashScreenActivity){it->
            it.locationSettingsStates
            Handler().postDelayed({

                //  if(prefManager!!.isValid().equals("true")){

                //   startActivity(Intent(this, DashBoard::class.java))
                // }else{
                startActivity(Intent(this, WelcomeScreen::class.java))
                //    }

            },3000)
        }

        task.addOnFailureListener(this@SplashScreenActivity) { e ->
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // grantLocPer()

                    // grantLocPer()
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    *//*  prefManager?.setPermissionDeniedCount(1)
                      if(prefManager?.getPermissionDeniedCount()!! >=1){
                          permissionSettingDialog()
                      }*//*
                    e.startResolutionForResult (this@SplashScreenActivity, REQUEST_CHECK_SETTINGS)

                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }

            }
        }
    }



    @SuppressLint("SuspiciousIndentation")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if (grantResults.isNotEmpty() && grantResults[0] ==
            PackageManager.PERMISSION_GRANTED
        ) {
            //  checkLocationService()


        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                val dialog = Dialog(this@SplashScreenActivity)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.permission_layout)
                val body = dialog.findViewById(R.id.error) as TextView
                body.text = "Warning! We are unable to continue if you want  you have give app permission manually"
                // body.text = title
                val yesBtn = dialog.findViewById(R.id.ok) as Button
                yesBtn.setOnClickListener {
                    dialog.dismiss()
                    prefManager?.setPermissionDeniedCount(1)
                    if(prefManager?.getPermissionDeniedCount()!! >=1){
                        permissionSettingDialog()
                    }
                    ActivityCompat.requestPermissions(
                        this@SplashScreenActivity,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)

                }

                dialog.show()
                val window: Window? = dialog.getWindow()
                window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            }


        }
    }




    private fun isLocationPermissionGranted(): Boolean {
        return !(ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val states = LocationSettingsStates.fromIntent(data!!)
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> when (resultCode) {
                RESULT_OK ->                     // All required changes were successfully made
                    // Toast.makeText(baseContext, "All good", Toast.LENGTH_SHORT).show()

                    Handler().postDelayed({

                        //  if(prefManager!!.isValid().equals("true")){

                        //   startActivity(Intent(this, DashBoard::class.java))
                        // }else{
                        startActivity(Intent(this, WelcomeScreen::class.java))
                        //    }

                    },5000)
                RESULT_CANCELED -> {
                    checkLocationService()
                }
                else -> {}
            }
        }
    }

    override fun onResume(){
        super.onResume()
        grantLocPer()
        if(goneToSettings) {
            var intent = Intent(this, SplashScreenActivity::class.java)
            finish()
            goneToSettings=false
            startActivity(intent)
        }
    }
    private fun permissionSettingDialog() {
        val alertDialog = AlertDialog.Builder(
            this
        )
        alertDialog.setTitle("Alert...")
        alertDialog.setMessage("Please location permission is required for the working of this app. Please turn on the location from settings. ")
        alertDialog.setPositiveButton("Yes"){
                dialog: DialogInterface?, which:Int->
            run {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
                goneToSettings=true

            }
        }
        alertDialog.show()
    }*/

}

