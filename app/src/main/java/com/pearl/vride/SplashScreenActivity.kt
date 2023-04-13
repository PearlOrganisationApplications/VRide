package com.pearl.vride

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.figgo.customer.pearlLib.PrefManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStates
import com.pearl.test5.R

class SplashScreenActivity : AppCompatActivity() {
    private val REQUEST_CHECK_SETTINGS: Int=101
    private lateinit var locationRequest: LocationRequest
    var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=101
    var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION2=102
    private val REQUEST_CODE = 101
    lateinit var pref: PrefManager
    var count = 0
    var permissionsG = 0
    var permissionsG2 = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        pref = PrefManager(this@SplashScreenActivity)




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


    fun grantLocPer() {

        if (isLocationPermissionGranted()) {






          //  checkLocationService()
        }

        else {
            ActivityCompat.requestPermissions(
                this@SplashScreenActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION,
                  ),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)

        }
    }
    private fun isLocationPermissionGranted(): Boolean {
        return !(ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED


                )
    }
    private fun isLocationPermissionGranted2(): Boolean {
        return !(ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED


                )
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION){
        if (grantResults.isNotEmpty() && grantResults[0] ==
            PackageManager.PERMISSION_GRANTED
        ) {

            permissionsG = 1
            //  checkLocationService()
            if (isLocationPermissionGranted2()) {

            } else {
                ActivityCompat.requestPermissions(
                    this@SplashScreenActivity,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    ),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION2)

            }



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
                    body.text =
                        "Warning! We are unable to continue if you want  you have give app permission manually"
                    // body.text = title
                    val yesBtn = dialog.findViewById(R.id.ok) as Button
                   // val noBtn = dialog.findViewById(R.id.no) as Button
                    yesBtn.setOnClickListener {
                        dialog.dismiss()
                        ActivityCompat.requestPermissions(
                            this@SplashScreenActivity,
                            arrayOf(
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION
                            ),
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                        )

                    }

                    dialog.show()
                    val window: Window? = dialog.getWindow()
                    window?.setLayout(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )



            }else{
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:$packageName")
                )
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        }
        else if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION2){
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {

                permissionsG2 = 1
                //  checkLocationService()




            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {


                        val dialog = Dialog(this@SplashScreenActivity)
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.permission_layout)
                        val body = dialog.findViewById(R.id.error) as TextView
                        body.text =
                            "Warning! We are unable to continue if you want  you have give app permission manually"
                        // body.text = title
                        val yesBtn = dialog.findViewById(R.id.ok) as Button
                       // val noBtn = dialog.findViewById(R.id.no) as Button
                        yesBtn.setOnClickListener {
                            dialog.dismiss()
                            ActivityCompat.requestPermissions(
                                this@SplashScreenActivity,
                                arrayOf(
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ),
                                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION2
                            )

                        }

                        dialog.show()
                        val window: Window? = dialog.getWindow()
                        window?.setLayout(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        count++



                }else{
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:$packageName")
                    )
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val states = LocationSettingsStates.fromIntent(data!!)
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> when (resultCode) {
                RESULT_OK ->                     // All required changes were successfully made
                    // Toast.makeText(baseContext, "All good", Toast.LENGTH_SHORT).show()

                    if (permissionsG == 1 && permissionsG2 == 1) {
                        Handler().postDelayed({

                            //  if(prefManager!!.isValid().equals("true")){

                            //   startActivity(Intent(this, DashBoard::class.java))
                            // }else{
                            if(pref.getLogin().equals("yes")) {
                                startActivity(Intent(this, MainActivity::class.java))
                            }else{
                                startActivity(Intent(this, WelcomeScreen::class.java))

                            }
                            //    }

                        }, 5000)
                    }
                RESULT_CANCELED -> {
                   // checkLocationService()
                }
                else -> {}
            }
        }
    }

    override fun onResume(){
        super.onResume()
        if (ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this@SplashScreenActivity, "Location permission disable cannot allow to continue", Toast.LENGTH_SHORT).show()
        }else{
            permissionsG = 1
            if (isLocationPermissionGranted2()) {






                //  checkLocationService()
            }

            else {
                ActivityCompat.requestPermissions(
                    this@SplashScreenActivity,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    ),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION2)

            }
            Toast.makeText(this@SplashScreenActivity, "Location permission enabled", Toast.LENGTH_SHORT).show()

        }
        if (ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this@SplashScreenActivity, "File permmision disable cannot allow to continue", Toast.LENGTH_SHORT).show()
        }else{
            permissionsG2 =1

            Toast.makeText(this@SplashScreenActivity, "File permission enabled ", Toast.LENGTH_SHORT).show()

        }
        if (permissionsG == 1 && permissionsG2 == 1){
            Handler().postDelayed({

                //  if(prefManager!!.isValid().equals("true")){

                //   startActivity(Intent(this, DashBoard::class.java))
                // }else{
                if(pref.getLogin().equals("yes")) {
                    startActivity(Intent(this, MainActivity::class.java))
                }else{
                    startActivity(Intent(this, WelcomeScreen::class.java))

                }


            }, 5000)
        }else {
            grantLocPer()
        }

    }


}