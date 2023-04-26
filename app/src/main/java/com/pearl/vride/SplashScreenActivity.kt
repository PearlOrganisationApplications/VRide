package com.pearl.vride

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.figgo.customer.pearlLib.PrefManager
import com.google.android.gms.location.LocationRequest
import com.pearl.test5.R


class SplashScreenActivity : AppCompatActivity() {
    private val REQUEST_CHECK_SETTINGS: Int=101
    private lateinit var locationRequest: LocationRequest
    var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=101
    var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION2=102
    private val REQUEST_CODE = 101
    lateinit var pref: PrefManager
    var count = 0
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
    val REQUEST_ID_MULTIPLE_PERMISSIONS1 = 2
    var type = ""
    private var perm = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        pref = PrefManager(this@SplashScreenActivity)


            startActivity(Intent(this, PermissionActivity::class.java))





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
    private fun checkAndRequestPermissions1(): Boolean {

        val locationPermission =
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray<String>(),
                REQUEST_ID_MULTIPLE_PERMISSIONS1
            )
            return false
        }
        return true
    }

    private fun checkAndRequestPermissions(): Boolean {

        val locationPermission =
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray<String>(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

/*
    fun grantLocPer() {

        if (isLocationPermissionGranted()) {


        }
        else {
            type = "1"
            ActivityCompat.requestPermissions(
                this@SplashScreenActivity,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)

        }
    }
*/
   /* private fun isLocationPermissionGranted(): Boolean {
        return !(ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED


                )
    }
    private fun isLocationPermissionGranted2(): Boolean {
        return !(ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@SplashScreenActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                )
    }
*/
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {
                val perms: MutableMap<String, Int> = HashMap()
                // Initialize the map with both permissions

                perms[android.Manifest.permission.ACCESS_FINE_LOCATION] = PackageManager.PERMISSION_GRANTED
                // Fill with actual results from user
                if (grantResults.size > 0) {
                    var i = 0
                    while (i < permissions.size) {
                        perms[permissions[i]] = grantResults[i]
                        i++
                    }
                    // Check for both permissions
                    if ( perms[android.Manifest.permission.ACCESS_FINE_LOCATION] == PackageManager.PERMISSION_GRANTED
                    ) {





                        val locationPermission =
                            ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        val listPermissionsNeeded: MutableList<String> = ArrayList()
                        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
                            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                        if (!listPermissionsNeeded.isEmpty()) {
                            ActivityCompat.requestPermissions(
                                this,
                                listPermissionsNeeded.toTypedArray<String>(),
                                REQUEST_ID_MULTIPLE_PERMISSIONS
                            )

                        }


                       // Log.d(TAG, "sms & location services permission granted")
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                      //  Log.d(TAG, "Some permissions are not granted ask again ")
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if ( ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        ) {
                            showDialogOK("SMS and Location Services Permission required for this app",
                                DialogInterface.OnClickListener { dialog, which ->
                                    when (which) {
                                        DialogInterface.BUTTON_POSITIVE -> checkAndReques()
                                        DialogInterface.BUTTON_NEGATIVE -> {}
                                    }
                                })
                        } else {
                            Toast.makeText(
                                this,
                                "Go to settings and enable permissions",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }

            REQUEST_ID_MULTIPLE_PERMISSIONS1 -> {
                val perms: MutableMap<String, Int> = HashMap()
                // Initialize the map with both permissions
                perms[android.Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                perms[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                // Fill with actual results from user
                if (grantResults.size > 0) {
                    var i = 0
                    while (i < permissions.size) {
                        perms[permissions[i]] = grantResults[i]
                        i++
                    }
                    // Check for both permissions
                    if (perms[android.Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED

                    ) {


                        // Log.d(TAG, "sms & location services permission granted")
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        //  Log.d(TAG, "Some permissions are not granted ask again ")
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if ( ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        ) {
                            showDialogOK("SMS and Location Services Permission required for this app",
                                DialogInterface.OnClickListener { dialog, which ->
                                    when (which) {
                                        DialogInterface.BUTTON_POSITIVE -> checkAndReques()
                                        DialogInterface.BUTTON_NEGATIVE -> {}
                                    }
                                })
                        } else {
                            Toast.makeText(
                                this,
                                "Go to settings and enable permissions",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }
    }

    private fun checkAndReques(){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }
    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }
/*
    @SuppressLint("SuspiciousIndentation")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION){
        if (grantResults.isNotEmpty() && grantResults[0] ==
            PackageManager.PERMISSION_GRANTED
        ) {


                pref.setLocPer(true)


            //  checkLocationService()
            if (isLocationPermissionGranted()) {


                if (isLocationPermissionGranted2()){
                    if (count == 0) {
                        if (pref.getLogin().equals("yes")) {
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            startActivity(Intent(this, WelcomeScreen::class.java))

                        }
                        count++
                    }
                }else{
                    type = "2"
                    ActivityCompat.requestPermissions(
                        this@SplashScreenActivity,
                        arrayOf(
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        ),
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION2)
                }
            }
            else {
                type = "1"
                ActivityCompat.requestPermissions(
                    this@SplashScreenActivity,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    ),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)

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



                    pref.setFile(true)


                //  checkLocationService()
                if (isLocationPermissionGranted()) {


                    if (isLocationPermissionGranted2()){
                        if (count == 0) {
                            if (pref.getLogin().equals("yes")) {
                                startActivity(Intent(this, MainActivity::class.java))
                            } else {
                                startActivity(Intent(this, WelcomeScreen::class.java))

                            }
                            count++
                        }
                    }else{
                        type = "2"
                        ActivityCompat.requestPermissions(
                            this@SplashScreenActivity,
                            arrayOf(
                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            ),
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION2)
                    }
                }
                else {
                    type = "1"
                    ActivityCompat.requestPermissions(
                        this@SplashScreenActivity,
                        arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        ),
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)

                }



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



                     val dialog2 = Dialog(this@SplashScreenActivity)
                        dialog2.setCancelable(false)
                        dialog2.setContentView(R.layout.permission_layout)
                        val body = dialog2.findViewById(R.id.error) as TextView
                        body.text =
                            "Warning! We are unable to continue if you want  you have give app permission manually"
                        // body.text = title
                        val yesBtn = dialog2.findViewById(R.id.ok) as Button
                        // val noBtn = dialog.findViewById(R.id.no) as Button
                        yesBtn.setOnClickListener {
                            dialog2.dismiss()
                            ActivityCompat.requestPermissions(
                                this@SplashScreenActivity,
                                arrayOf(
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ),
                                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION2
                            )



                        dialog2.show()
                        val window: Window? = dialog2.getWindow()
                        window?.setLayout(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )


                    }

                }else{
                    if (!pref.isFile()) {
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
    }
*/

  /*  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val states = LocationSettingsStates.fromIntent(data!!)
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> when (resultCode) {
                RESULT_OK ->                     // All required changes were successfully made
                    // Toast.makeText(baseContext, "All good", Toast.LENGTH_SHORT).show()
                   try {


                       if (pref.isLovPer() && pref.isFile()) {
                           Handler().postDelayed({

                               //  if(prefManager!!.isValid().equals("true")){

                               //   startActivity(Intent(this, DashBoard::class.java))
                               // }else{
                               if (count == 0) {
                                   if (pref.getLogin().equals("yes")) {
                                       startActivity(Intent(this, MainActivity::class.java))
                                   } else {
                                       startActivity(Intent(this, WelcomeScreen::class.java))

                                   }
                                   count++
                               }
                               //    }

                           }, 5000)
                       } else if (pref.isLovPer()) {
                           ActivityCompat.requestPermissions(
                               this@SplashScreenActivity,
                               arrayOf(
                                   android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                   android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                               ),
                               PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION2
                           )
                       } else {
                          //
                       //
                       // Toast.makeText(baseContext, "All good", Toast.LENGTH_SHORT).show()
                       }
                   }catch (e:Exception){

                   }
                        RESULT_CANCELED -> {
                   // checkLocationService()
                }
                else -> {}
            }
        }
    }*/
    private fun checkWriteExternalPermission(): Boolean {
        val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
        val res: Int = this.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }
    private fun checkWriteExternalPermission1(): Boolean {
        val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
        val res: Int = this.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }
    override fun onResume(){
        super.onResume()


       /* if(count == 0) {
            checkAndRequestPermissions()
            count++
        }else {
            if (checkWriteExternalPermission()) {
                if (checkWriteExternalPermission1()) {
                    if (pref.getLogin().equals("yes")) {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        startActivity(Intent(this, WelcomeScreen::class.java))

                    }
                } else {

                    checkAndRequestPermissions1()

                    // Toast.makeText(baseContext, "Go to settings and enable permissions", Toast.LENGTH_SHORT).show()
                }
            } else {
                //  Toast.makeText(baseContext, "Go to settings and enable permissions", Toast.LENGTH_SHORT).show()

                checkAndRequestPermissions()
            }
        }*/
/*}

        if(pref.getPerm().equals("yes")){
            grantLocPer()
            pref.setPerm("no")
        }else {


            if (pref.isLovPer()) {
                //  dialog.dismiss()
                if (pref.isFile()) {
                    if (count == 0) {
                        if (pref.getLogin().equals("yes")) {
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            startActivity(Intent(this, WelcomeScreen::class.java))

                        }
                        count++
                    }
                } else {
                    ActivityCompat.requestPermissions(
                        this@SplashScreenActivity,
                        arrayOf(
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        ),
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION2
                    )
                }

            } else {

                ActivityCompat.requestPermissions(
                    this@SplashScreenActivity,
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    ),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION2
                )


            }
            if (pref.isFile()) {
                // dialog2.dismiss()
                if (pref.isLovPer()) {
                    if (count == 0) {
                        if (pref.getLogin().equals("yes")) {
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            startActivity(Intent(this, WelcomeScreen::class.java))

                        }
                        count++
                    }
                } else {
                    ActivityCompat.requestPermissions(
                        this@SplashScreenActivity,
                        arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        ),
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                    )
                }

            } else {
                ActivityCompat.requestPermissions(
                    this@SplashScreenActivity,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    ),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                )

            }

            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                pref.setLocPer(true)
            }
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                pref.setFile(true)
            }


            if (pref.isLovPer() && pref.isFile()) {
                Handler().postDelayed({

                    //  if(prefManager!!.isValid().equals("true")){

                    //   startActivity(Intent(this, DashBoard::class.java))
                    // }else{
                    if (count == 0) {
                        if (pref.getLogin().equals("yes")) {
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            startActivity(Intent(this, WelcomeScreen::class.java))

                        }
                        count++
                    }


                }, 5000)
            } else {
                deleteCache(this@SplashScreenActivity)
                grantLocPer()
            }
        }
    }
    fun deleteCache(context: Context) {
        try {
            val dir: File = context.getCacheDir()
            deleteDir(dir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }*/
    }

}