package com.pearl.v_ride

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.pearl.test5.R
import com.pearl.v_ride_lib.BaseClass
import android.view.View
import com.pearl.ui.DocumentActivity
import com.pearl.v_ride_lib.Global

import com.pearl.v_ride_lib.PrefManager

class MainActivity : BaseClass() {

    private companion object {
        private const val RC_SIGN_IN = 1
        private const val TAG = "GOOGLEAUTH"
    }
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dialog: Dialog
    lateinit var gooleSignIn: SignInButton
//    lateinit var forgotPassword: TextView
    lateinit var signup: TextView
    lateinit var login: Button
    private val REQUEST_CODE = 101
    private lateinit var usrID:EditText

   /* lateinit var verify: Button
    lateinit var cancel: ImageView*/
    lateinit var loginOtp: EditText
    lateinit var otpBt: Button
    lateinit var prefManager: PrefManager

    override fun setLayoutXml() {
        setContentView(R.layout.activity_main)

    }

    override fun initializeViews() {
        gooleSignIn = findViewById(R.id.google_signIn)
        signup = findViewById<TextView>(R.id.signupBt)
        login = findViewById<Button>(R.id.loginBT)
//        forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        mAuth = FirebaseAuth.getInstance()
        loginOtp = findViewById(R.id.loginOtp)
        otpBt = findViewById(R.id.otpBT)
        usrID = findViewById(R.id.userID)

    }



    override fun initializeClickListners() {

           gooleSignIn.setOnClickListener {
               signIn()
           }
        signup.setOnClickListener {
            startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
//            finish()
        }


            otpBt.setOnClickListener {
                if(validateNumber(usrID)) {

                loginOtp.visibility = View.VISIBLE
                login.visibility = View.VISIBLE
                otpBt.visibility = View.GONE
            }
        }

        login.setOnClickListener {
            prefManager.setLogin(true)
/*           dialog.setContentView(R.layout.activity_forgot_password)
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.setCancelable(false)
            dialog.window?.attributes?.windowAnimations = R.style.animation

            verify = dialog.findViewById(R.id.otp_Verify_button)
            cancel = dialog.findViewById(R.id.view_cancel_dialog)

            verify.setOnClickListener {
                dialog.dismiss()
                Toast.makeText(this@MainActivity, "okay clicked", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@MainActivity,HomeScreen::class.java))
            }

            cancel.setOnClickListener {
                dialog.dismiss()
                Toast.makeText(this@MainActivity, "Cancel clicked", Toast.LENGTH_SHORT).show()
            }

            dialog.show()*/



             startActivity(Intent(this,HomeScreen::class.java))



        }
    }

    override fun initializeInputs() {
    }

    override fun initializeLabels() {

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) 

        val isConnected = isNetworkConnected(this.applicationContext)

        prefManager = PrefManager(this)

        setLayoutXml()
        initializeViews()
        initializeClickListners()
        initializeInputs()
        initializeLabels()
        internetChangeBroadCast()



//        validateNumber+

        if(!isConnected){

            val alertDialog2: AlertDialog.Builder = AlertDialog.Builder(
                this@MainActivity
            )
            alertDialog2.setTitle("No Internet Connection")
            alertDialog2.setPositiveButton("Try Again",
                DialogInterface.OnClickListener { dialog, which ->
                    val intent = intent
                    finish()
                    startActivity(intent)
                })
            alertDialog2.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                    finishAffinity()
                    System.exit(0)
                })
            alertDialog2.setCancelable(false)
            alertDialog2.show()

        }



       /* if (ActivityCompat.checkSelfPermission(
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
/*        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
            val requestCode = 1
            ActivityCompat.requestPermissions(this, permissions, requestCode)
        }*/


            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_wait)
            dialog.setCanceledOnTouchOutside(false)
        }

    // Getting the Button Click



/*    private fun googleBtnUi() {
        // Get a reference to the sign-in button
//        gooleSignIn = findViewById<SignInButton>(R.id.google_signIn)

        // Set an onClick listener for the sign-in button
        gooleSignIn.setOnClickListener {
            signIn()
        }

        // Loop through the child views of the sign-in button
        for (i in 0 until gooleSignIn.childCount) {
            val v = gooleSignIn.getChildAt(i)

            // Check if the child view is a TextView
            if (v is TextView) {
                // If it is, cast it to a TextView and set its properties
                val tv = v
                tv.textSize = 14f
                tv.setTypeface(null, Typeface.NORMAL)
                tv.text = "My Text"
                tv.setTextColor(Color.parseColor("#FFFFFF"))
//                tv.background = resources.getDrawable(R.drawable.ic_launcher)
                tv.isSingleLine = true
                tv.setPadding(15, 15, 15, 15)

                return // Exit the loop
            }
        }
    }*/

    private fun validateForm(){

 /*
      baseclass.validateName(binding.drivername)
        baseclass.validateNumber(binding.drivermobileno)
        baseclass.validateAadharNo(binding.driverAdharNo)
        baseclass.validatePanNo(binding.driverPanNo)
        baseclass.validateDLNo(binding.driverdlno)

        if (baseclass.validateName(binding.drivername)&&baseclass.validateNumber(binding.drivermobileno)&&baseclass.validateDLNo(binding.driverdlno)&&baseclass.validatePanNo(binding.driverPanNo)&&baseclass.validateAadharNo(binding.driverAdharNo)){

          Navigation.findNavController(requireView()).navigate(R.id.action_figgo_Capton_to_driverCabDetailsFragment,args)

        }*/

    }
    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            dialog.show()
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                //Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account!!.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                dialog.dismiss()
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
// Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth.currentUser
                    val i = Intent(this@MainActivity, DocumentActivity::class.java)
                    startActivity(i)
                    finish()
                    dialog.dismiss()
// updateUI(user);
                } else {
// If sign in fails, display a message to the user.
// Log.w(TAG, "signInWithCredential:failure", task.getException());
// Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
// updateUI(null);
                    dialog.dismiss()
                    Toast.makeText(this@MainActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
// ...
            }
    }
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

   /* override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
            }
        }
    }*/
/*   override fun onRequestPermissionsResult(
       requestCode: Int,
       permissions: Array<String>,
       grantResults: IntArray
   ) {
       super.onRequestPermissionsResult(requestCode, permissions, grantResults)
       when (requestCode) {
           REQUEST_CODE -> {
               if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   // Permission granted, do something
               } else {
                   // Permission denied, re-prompt the user for permission
                   ActivityCompat.requestPermissions(
                       this,
                       arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                       REQUEST_CODE
                   )
               }
           }
       }
   }*/

/*
   private fun checkLocationPermission() {
       if (ContextCompat.checkSelfPermission(
               this,
               Manifest.permission.ACCESS_FINE_LOCATION
           ) != PackageManager.PERMISSION_GRANTED
       ) {
           if (ActivityCompat.shouldShowRequestPermissionRationale(
                   this,
                   Manifest.permission.ACCESS_FINE_LOCATION
               )
           ) {
               // Explain why the permission is necessary
               AlertDialog.Builder(this)
                   .setTitle("Location Permission Required")
                   .setMessage("This app requires location permission to function properly.")
                   .setPositiveButton("OK") { _, _ ->
                       // Request the permission again
                       ActivityCompat.requestPermissions(
                           this,
                           arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                           REQUEST_CODE
                       )
                   }
                   .setNegativeButton("Cancel", null)
                   .show()
           } else {
               // Show a message and open the app's settings page
               AlertDialog.Builder(this)
                   .setTitle("Location Permission Denied")
                   .setMessage("This app requires location permission to function properly. Please grant the permission manually.")
                   .setPositiveButton("Open Settings") { _, _ ->
                       val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                       intent.data = Uri.fromParts("package", packageName, null)
                       startActivity(intent)
                   }
                   .setNegativeButton("Cancel", null)
                   .show()
           }
       } else {
           // Permission granted, do something
       }
   }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, do something
                } else {
                    // Permission denied, handle the lack of permission
                    checkLocationPermission()
                }
            }
        }
    }

*/


}