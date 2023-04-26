package com.pearl.vride

import android.Manifest
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.os.Bundle

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.provider.Settings
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat

import com.pearl.test5.R
import com.pearl.v_ride.MainActivity
import com.pearl.v_ride.WelcomeScreen
import com.pearl.v_ride_lib.PrefManager


class PermissionActivity : AppCompatActivity() {
    var alertDialogBuilder: AlertDialog.Builder? = null
    var alertDialog: AlertDialog? = null
    lateinit var location: LinearLayout
    lateinit var call: LinearLayout
    lateinit var storage: LinearLayout
    lateinit var storageW: ImageView
    lateinit var storageA: ImageView
    lateinit var callW: ImageView
    lateinit var callA: ImageView
    lateinit var pref: PrefManager
    lateinit var locW: ImageView
    lateinit var locA: ImageView
    lateinit var verify: Button
    var locCheck: Boolean = false
    var callCheck: Boolean = false
    var storageCheck: Boolean = false
    var gonetoSettings: Boolean = false
    var cond = 0
        var cond1 =0

    var cond2 = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        gonetoSettings = false
        pref = PrefManager(this@PermissionActivity)

        location = findViewById(R.id.locpermission)
        call = findViewById(R.id.call)
        storage = findViewById(R.id.storage)
        verify = findViewById(R.id.verify)
        storageA = findViewById(R.id.img_sto_check)
        storageW = findViewById(R.id.img_sto)
        callA = findViewById(R.id.img_call_check)
        callW= findViewById(R.id.img_call)
        locW = findViewById(R.id.img_loc)
        locA = findViewById(R.id.img_loc_check)
        alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder!!.setTitle("Alert...")
        alertDialogBuilder!!.setMessage("Please location permission is required for the working of this app. Please turn on the location from settings.")
        alertDialogBuilder!!.setPositiveButton("Yes") { dialog, which ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
            dialog.dismiss()
        }

        alertDialog = alertDialogBuilder!!.create()
        locCheck = false
        callCheck = false
        storageCheck = false
        location.setOnClickListener(View.OnClickListener {
            if (permissiongranted()) {
              pref.setLocPer(true)
            }
        })
        call.setOnClickListener(View.OnClickListener {
            if (callgranted()) {
               pref.setCallPer(true)
            }
        })
        storage.setOnClickListener(View.OnClickListener {
            if (storagegranted()) {
                pref.setLocPer(true)
            }
        })
        verify.setOnClickListener(View.OnClickListener {
            if (pref.getLocPer() && pref.getCallPer() && pref.getReadPer()){
                pref.setPr(true)
                /*if (pref.getLogin().equals("yes")) {

                } else {
                    startActivity(Intent(this, WelcomeScreen::class.java))

                }*/
                startActivity(Intent(this, MainActivity::class.java))

            }
        })

    }
    private fun storagegranted(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            storageCheck = true
            return true
        } else {
            // permission not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_STORAGE_PERMISSION
            )
        }
        return false
    }

    private fun callgranted(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            pref.setCallPer(true)
            return true
        } else {
            // permission not granted, request it
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 133)
        }
        return false
    }

    private fun permissiongranted(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            pref.setCallPer(true)


            return true
        } else {
            // permission not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locCheck = true
                locA.visibility = View.VISIBLE
                locW.visibility = View.GONE
                cond = 1
                pref.setFile(true)
                alertDialog!!.dismiss()
            } else {
                showSetting()
            }
        } else if (requestCode == 133) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callCheck = true
                callA.visibility = View.VISIBLE
                callW.visibility = View.GONE
                cond = 1
                alertDialog!!.dismiss()
                pref.setCall(true)
            } else {
                showSetting()
            }
        }
        if (requestCode == 123) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                storageCheck = true
                storageA.visibility = View.VISIBLE
                storageW.visibility = View.GONE
                pref.setFile(true)
                cond2 = 1

                alertDialog!!.dismiss()
            } else {

                showSetting()
                // permission denied
            }
        }
    }

    fun showSetting() {
        pref.setSetting(true)
        gonetoSettings = true
        alertDialog!!.show()
    }
    private fun isLocationPermissionGranted(): Boolean {
        return !(ActivityCompat.checkSelfPermission(this@PermissionActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@PermissionActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
    }

    private fun isFileGranted(): Boolean {
        return !(ActivityCompat.checkSelfPermission(this@PermissionActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@PermissionActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
    }
    private fun isCallGranted(): Boolean {
        return !(ActivityCompat.checkSelfPermission(this@PermissionActivity, android.Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)
    }


    override fun onResume() {
        super.onResume()

        if (isLocationPermissionGranted()) {
            pref.setLocPer(true)
            locA.visibility = View.VISIBLE
            locW.visibility = View.GONE
        }

        if (isFileGranted()) {
            pref.setReadPer(true)
            storageA.visibility = View.VISIBLE
            storageW.visibility = View.GONE
        }

        if (isCallGranted()) {
            pref.setCallPer(true)
            callA.visibility = View.VISIBLE
            callW.visibility = View.GONE
        }

        if (pref.getSetting()) {
            if (!pref.getLocPer() && !pref.getCallPer() && !pref.getReadPer()) {
                showSetting()
            }
        }


       /*  if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {

        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locA.visibility = View.VISIBLE
            locW.visibility = View.GONE
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ){
            callA.visibility = View.VISIBLE
            callW.visibility = View.GONE
        }
        gonetoSettings = true*/
      /*  if (gonetoSettings!!) {
            if (permissiongranted() && callgranted() && storagegranted())
                if (pref.getLogin().equals("yes")) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, WelcomeScreen::class.java))

                }
                Toast.makeText(
                this,
                "All permission are granted",
                Toast.LENGTH_SHORT
            ).show()}
        else showSetting()
            gonetoSettings = false*/

    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 101
        private const val REQUEST_STORAGE_PERMISSION = 123
    }
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
    private fun checkWriteExternalPermission2(): Boolean {
        val permission = android.Manifest.permission.CALL_PHONE
        val res: Int = this.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }
}