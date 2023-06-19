package com.pearl.v_ride

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.*
import android.text.Html
import android.util.Log
import android.view.View

import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.pearl.v_ride_lib.BaseClass
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.pearl.common.retrofit.data_model_class.LoginInfo
import com.pearl.common.retrofit.rest_api_interface.LoginApi
import com.pearl.ui.DocumentActivity
import com.pearl.ui.DocumentStatus
import com.pearl.v_ride_lib.Global

import com.pearl.v_ride_lib.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

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
    private lateinit var usrID: EditText
    private lateinit var progressBar: ProgressBar
    var storedVerificationId = ""

    /* lateinit var verify: Button
     lateinit var cancel: ImageView*/
    lateinit var loginOtp: EditText
    lateinit var otpBt: Button
    lateinit var prefManager: PrefManager

    //    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var phoneNumber: String
    var verifyOTP = ""
    var otpCode = ""
    var vOTP = ""
    lateinit var credential: PhoneAuthCredential
    lateinit var resentToken: PhoneAuthProvider.ForceResendingToken
    lateinit var resend_otp: TextView
    lateinit var view_timer: TextView
    lateinit var textView: TextView
    lateinit var cTimer: CountDownTimer
    private lateinit var loadingDialog: com.pearl.v_ride.Dialog
    var text = ""

    override fun setLayoutXml() {
        setContentView(R.layout.activity_main)
        prefManager = PrefManager(this)
    }

    override fun initializeViews() {
        gooleSignIn = findViewById(R.id.google_signIn)
        signup = findViewById<TextView>(R.id.signupBt)
        login = findViewById<Button>(R.id.loginBT)
//      forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        mAuth = FirebaseAuth.getInstance()
        loginOtp = findViewById(R.id.loginOtp)
        otpBt = findViewById(R.id.otpBT)
        usrID = findViewById(R.id.userID)
        textView = findViewById(R.id.textView)
        phoneNumber = usrID.text.toString()
        progressBar = findViewById(R.id.progressBar)
        resend_otp = findViewById(R.id.resend_otp)
        view_timer = findViewById(R.id.view_timer)
        loadingDialog = com.pearl.v_ride.Dialog(this)
//        checkLogin()

    }

    @SuppressLint("SuspiciousIndentation")
    override fun initializeClickListners() {

         text = "<font color=#000000>By clicking \"Login\" above, you agree to our   </font> " +
                "<font color=#0C805F>terms &amp; conditiions</font><font color=#000000> and </font>" +
                "<font color=#0C805F>privacy policy.</font>"

        textView.setText(Html.fromHtml(text))

        gooleSignIn.setOnClickListener {
            signIn()
        }

        signup.setOnClickListener {
            startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
//            finish()
        }

        otpBt.setOnClickListener {

            buttonEffect(otpBt)
            phoneNumber = usrID.text.toString().trim()
//                if(validateNumber(usrID)) {


            if (validateNumber(usrID)) {
//                        startPhoneNumberVerification("+918979441470")
                /*   loginOtp.visibility = View.VISIBLE
                   login.visibility = View.VISIBLE
                   otpBt.visibility = View.GONE*/

                if (phoneNumber.length == 10) {
                    if (phoneNumber == "1234567890" /*||phoneNumber == "1122334455"*/) {

                        view_timer.visibility = View.VISIBLE
                        phoneNumber = "+91$phoneNumber"
//                        progressBar.visibility = View.VISIBLE
//                                loadingDialog.startLoadingDialog()

//                                Handler(Looper.getMainLooper()).postDelayed({
//                                    loadingDialog.dismissDialog()

                        val options = PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(phoneNumber) // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(this@MainActivity) // Activity (for callback binding)
                                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                                .build()
                        PhoneAuthProvider.verifyPhoneNumber(options)
                        loadingDialog.startLoadingDialog()
//                        startTimer()
//                                },2500)
//                                checkLogin()
                    } else {
                        loadingDialog.startLoadingDialog()

                        checkLogin()
                    }
                } else {
                    Toast.makeText(this, "please Enter correct no", Toast.LENGTH_SHORT).show()
                }

            }
//                startTimer()
        }

 /*       if (::resentToken.isInitialized) {

        } else {
            resend_otp.visibility = View.GONE
        }*/
        resend_otp.setOnClickListener {
//                loginOtp.visibility = View.VISIBLE
            val options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .setForceResendingToken(resentToken)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

            resend_otp.visibility = View.GONE
            loadingDialog.startLoadingDialog()
            loginOtp.setText("")
//                           resentTVVisibility()
        }

        login.setOnClickListener {
            prefManager.setLogin(true)

            otpCode = loginOtp.getText().toString()
            if (otpCode.isNotEmpty()) {
                if (otpCode.length == 6) {
                    credential = PhoneAuthProvider.getCredential(verifyOTP, otpCode)
                    vOTP = verifyOTP
                    Log.d("OTPOTP", verifyOTP + "  " + otpCode)
                    if (vOTP == verifyOTP) {
//                        Toast.makeText(this@MainActivity, "Please enter correct 1 OTP", Toast.LENGTH_SHORT).show()
//                        loginOtp.visibility = View.GONE
//                        progressBar.visibility = View.VISIBLE
                        loadingDialog.startLoadingDialog()
                        signInWithPhoneAuthCredential(credential)
                    } else {
                        Toast.makeText(this@MainActivity, "Please enter correct OTP", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Please enter 6 digits OTP", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "Please enter the OTP", Toast.LENGTH_SHORT).show()
            }

//             startActivity(Intent(this,HomeScreen::class.java))

        }
    }

    override fun initializeInputs() {}

    override fun initializeLabels() {}

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLayoutXml()
        val isConnected = isNetworkConnected(this.applicationContext)
        initializeViews()
        initializeClickListners()
        initializeInputs()
        initializeLabels()
        internetChangeBroadCast()


//        resentTVVisibility()

//        validateNumber+

        if (!isConnected) {

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


    private fun validateForm() {
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
                        dialog.dismiss()
                        Toast.makeText(this@MainActivity, "Login failed", Toast.LENGTH_SHORT).show()
                    }

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

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
            loadingDialog.dismissDialog()
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            loadingDialog.dismissDialog()

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d("TAG", "verificationFailed ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Toast.makeText(this@MainActivity,"we are having Some internal issues please try again later",Toast.LENGTH_SHORT).show()
                Log.d("TAG", "FirebaseTooManyRequestsException ${e.toString()}")
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }

            // Show a message and update the UI

        }

        override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:$verificationId")
            loginOtp.visibility = View.VISIBLE
            login.visibility = View.VISIBLE
            otpBt.visibility = View.GONE
//            resend_otp.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            verifyOTP = verificationId
            vOTP = verificationId
            resentToken = token

            loadingDialog.dismissDialog()
            // Save verification ID and resending token so we can use them later
            /*       storedVerificationId = verificationId
                   resendToken = token*/
            view_timer.visibility = View.VISIBLE
            startTimer()
        }
        override fun onCodeAutoRetrievalTimeOut(verificationId: String) {
            super.onCodeAutoRetrievalTimeOut(verificationId)
            // Hide the resend_otp button
/*            resend_otp.visibility = View.VISIBLE
            signup_otpVerifyBT.visibility = View.GONE*/
            resend_otp.visibility = View.VISIBLE
//            resentTVVisibility()
//            startTimer()
        }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser != null) {
//            startActivity(Intent(this@MainActivity, HomeScreen::class.java))
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        loadingDialog.dismissDialog()
                        startActivity(Intent(this@MainActivity, HomeScreen::class.java))
                        finish()
                        val user = task.result?.user
                    } else {
                        // Sign in failed, display a message and update the UI
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                        // Update UI
                    }
//                    progressBar.visibility = View.VISIBLE
                    loadingDialog.dismissDialog()
                    Toast.makeText(this@MainActivity,"Please Enter valid otp ",Toast.LENGTH_SHORT).show()
                }
    }



    private fun resentTVVisibility() {
        loginOtp.setText("")
        resend_otp.visibility = View.GONE
        resend_otp.isEnabled = false

        Handler(Looper.myLooper()!!).postDelayed(
                Runnable {
                    resend_otp.visibility = View.VISIBLE
                    resend_otp.isEnabled = true
                }, 60000)
    }

    fun startTimer() {
        cTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                view_timer.text = "00:00: " + (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
//                view_timer.setText("Re send OTP!")
                view_timer.visibility = View.GONE
                resend_otp.visibility = View.VISIBLE

            }
        }
        cTimer.start()
    }

    fun checkLogin() {
        /*   if(phoneNumber == "")
           {
               Toast.makeText(this,"Please Enter Name",Toast.LENGTH_SHORT).show()
           }else{*/
        val retrofit = Retrofit.Builder()
                .baseUrl(Global.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val userService = retrofit.create(LoginApi::class.java)
        val parameters = mapOf("mobileNo" to phoneNumber)
        val call = userService.addUser(parameters)
        call.enqueue(object : Callback<LoginInfo> {
            override fun onResponse(call: Call<LoginInfo>, response: Response<LoginInfo>) {
                val createdUser = response.body()
                if (response.isSuccessful) {

                    Log.d("ResponseLogin ", createdUser.toString())
                    // Handle the created user object
                    prefManager.setToken(response.body()?.token.toString())
                    if (createdUser?.signin.equals("0")) {
                        // sign in
                        Handler().postDelayed({
                            // After 4 seconds
                            loadingDialog.dismissDialog()
                            val builder = AlertDialog.Builder(this@MainActivity)
                            builder.setTitle("Signup")
                                    .setMessage("You are not register user, SignIn first")
                                    .setPositiveButton("ok") { dialog, _ ->
                                        dialog.dismiss()
                                        startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
                                    }
                            val dialog = builder.create()
                            dialog.show()
                        }, 4000) // 4 seconds
                    }
                    else if (createdUser?.signin.equals("1") && createdUser?.profile.equals("-4") && createdUser?.verification.equals("0")) {
                        // profile no
                        Log.d("status1 ", "${createdUser?.profile}")
                        Handler().postDelayed({
                            // After 4 seconds
                            loadingDialog.dismissDialog()
                            val builder = AlertDialog.Builder(this@MainActivity)
                            builder.setTitle("Profile Details")
                                    .setMessage("You are not register user, fill verification form first")
                                    .setPositiveButton("ok") { dialog, _ ->
                                        dialog.dismiss()
                                        startActivity(Intent(this@MainActivity, DocumentStatus::class.java))
                                    }
                            val dialog = builder.create()
                            dialog.show()
                        }, 4000) // 4 seconds
                    }

                    /*else if(createdUser?.signin.equals("1") && createdUser?.profile.equals( "0") && createdUser?.verification.equals("0") ){
                        // only profile 1
                        val builder = AlertDialog.Builder(this@MainActivity)
                        builder.setTitle("Error")
                            .setMessage("You are not register user, fill verification form first")
                            .setPositiveButton("ok") { dialog, _ ->
                                dialog.dismiss()
                                startActivity(Intent(this@MainActivity,DocumentActivity::class.java).putExtra("key",1))
                            }
                        val dialog = builder.create()
                        dialog.show()
                    }*/

                    else if (createdUser?.signin.equals("1") && createdUser?.profile.equals("2") || createdUser?.profile.equals("1") && createdUser?.verification.equals("0")) {
                        // validation page
                        /* Log.d("status123 " ,"${createdUser?.profile}")
                         phoneNumber = "+91$phoneNumber"
                         progressBar.visibility = View.VISIBLE
                         val options = PhoneAuthOptions.newBuilder(mAuth)
                             .setPhoneNumber(phoneNumber) // Phone number to verify
                             .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                             .setActivity(this@MainActivity) // Activity (for callback binding)
                             .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                             .build()
                         PhoneAuthProvider.verifyPhoneNumber(options)*/
                        Handler().postDelayed({
                            // After 4 seconds
                            loadingDialog.dismissDialog()
                            startActivity(Intent(this@MainActivity, VerificationActivity::class.java))
                            finish()
                        }, 4000) // 4 seconds
                    }else if (createdUser?.signin.equals("1") && createdUser?.profile.equals("1") || createdUser?.profile.equals("2") && createdUser?.verification.equals("1")) {
                        // Handle the error response
                        loadingDialog.dismissDialog()
                        Log.d("ElseLogin ", "t.toString()")
                        val errorResponseCode = response.code()
                        val errorResponseBody = response.errorBody()?.string()
                        // Handle the error response code and body
                        Log.e("API Error", "Response Code: $errorResponseCode, Body: $errorResponseBody")
                        // Show a generic error message to the user
                        showErrorDialog("You can log in now you are verified user.", "OK")
                    } else {
                        //profile 2
                        Log.d("status12 ", "${createdUser?.profile}")
                        Handler().postDelayed({
                            // After 4 seconds
                            loadingDialog.dismissDialog()
                            val builder = AlertDialog.Builder(this@MainActivity)
                            builder.setTitle("Document Details")
                                .setMessage("Some Details are missing,\n please fill registration from first ")
                                .setPositiveButton("ok") { dialog, _ ->
                                    dialog.dismiss()
                                    startActivity(Intent(this@MainActivity, DocumentStatus::class.java))
                                }
                            val dialog = builder.create()
                            dialog.show()
                        }, 4000) // 4 seconds

                    }
                }

            }

            override fun onFailure(call: Call<LoginInfo>, t: Throwable) {
                Log.d("ErrorLogin ", t.toString())
                // Handle the network error
                if (t is IOException) {
                    // Network error occurred
                    Log.e("API Error", "Network error occurred", t)
                    // Show a generic network error message to the user
                    showErrorDialog("Network error. Please check your internet connection.", "OK")
                } else {
                    // Other types of errors occurred
                    Log.e("API Error", "Error occurred", t)
                    // Show a generic error message to the user
                    showErrorDialog("An error occurred. Please try again later.", "OK")
                }
            }
        })

    }

}