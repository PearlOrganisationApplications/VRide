package com.pearl.v_ride

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.pearl.common.retrofit.data_model_class.SignUpInfo
import com.pearl.common.retrofit.rest_api_interface.SignupApi
import com.pearl.ui.DocumentStatus
import com.pearl.v_ride_lib.BaseClass
import com.pearl.v_ride_lib.Global
import com.pearl.v_ride_lib.Global.baseUrl
import com.pearl.v_ride_lib.PrefManager
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class SignUpActivity : BaseClass() {

    private companion object {
        private const val RC_SIGN_IN = 1
        private const val TAG = "PhoneAUTH"
    }
    lateinit var prefManager: PrefManager
    lateinit var dob: EditText
    lateinit var signup: Button
    private val myCalendar = Calendar.getInstance()
    lateinit var verify: Button
    lateinit var cancel: ImageView
    lateinit var  dialog : Dialog
    lateinit var register: TextView
    lateinit var sName: EditText
    lateinit var sPhone: EditText
    lateinit var signup_otp: EditText
    lateinit var signup_otpVerifyBT: Button
    lateinit var signup_otpLL: LinearLayout
    private lateinit var signup_email: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var phoneNumber: String
    var verifyOTP = ""
    var otpCode = ""
    var vOTP = ""
    var full_name = ""
    var email = ""
    var signup_dob = ""
    val prefix = "+91"
    var tkn = ""
    lateinit var credential: PhoneAuthCredential
    private lateinit var loadingDialog: com.pearl.v_ride.Dialog
    override fun setLayoutXml() {
        setContentView(R.layout.activity_sign_up)
//        checkSignUp()

    }

    override fun initializeViews() {
        mAuth = FirebaseAuth.getInstance()
        signup = findViewById(R.id.signupBtn)
        register = findViewById<TextView>(R.id.already)
        dob= findViewById(R.id.signup_dob)
        sName = findViewById(R.id.signup_name)
        sPhone = findViewById(R.id.signup_phone)
        signup_email = findViewById(R.id.signup_email)
        signup_otp = findViewById(R.id.signup_otp)
        signup_otpLL = findViewById(R.id.signup_otpLL)
        signup_otpVerifyBT = findViewById(R.id.signup_otpVerifyBT)
        prefManager = PrefManager(this)
        loadingDialog = com.pearl.v_ride.Dialog(this)



        dialog = Dialog(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initializeClickListners() {
        dob.setOnClickListener {
            showDatePicker()
        }

        register.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
        }
        signup.setOnClickListener {

//            startActivity(Intent(this@SignUpActivity,DocumentActivity::class.java))
            phoneNumber = sPhone.text.toString().trim()
            full_name = sName.text.toString().trim()
            signup_dob = dob.text.toString().trim()
            Log.d("dob","$signup_dob,$phoneNumber, $full_name")
            loadingDialog.startLoadingDialog()
            checkSignUp()


           /* if (validateName(sName) && validateNumber(sPhone) && validateDob(dob)){
                dialog.setContentView(R.layout.activity_forgot_password)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.setCancelable(false)
            dialog.window?.attributes?.windowAnimations = R.style.animation

            verify = dialog.findViewById(R.id.otp_Verify_button)
            cancel = dialog.findViewById(R.id.view_cancel_dialog)

            verify.setOnClickListener {
                dialog.dismiss()
                Toast.makeText(this, "okay clicked", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SignUpActivity, DocumentActivity::class.java))
            }

            cancel.setOnClickListener {
                dialog.dismiss()
                Toast.makeText(this@SignUpActivity, "Cancel clicked", Toast.LENGTH_SHORT).show()
            }

            dialog.show()
        }*/

        }

   /*     if (sPhone.text.length == 10){
            signup_otpLL.visibility = View.VISIBLE
        }*/
       /* sPhone.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (!sPhone.text.startsWith(prefix)) {
                    sPhone.setText(prefix + sPhone.text)
                }
            } else {
                if (sPhone.text.startsWith(prefix)) {
                    sPhone.setText(sPhone.text.removePrefix(prefix))
                }
            }
        }*/
        // for otp verification
     /*   sPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                phoneNumber = s.toString().trim()
                if (phoneNumber.length == 10) {
                    signup_otpLL.visibility = View.VISIBLE
                    if (validateNumber(sPhone)) {
                        if (phoneNumber.length == 10){

                            phoneNumber = "+91$phoneNumber"
//                    progressBar.visibility = View.VISIBLE
                            val options = PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(phoneNumber) // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(this@SignUpActivity) // Activity (for callback binding)
                                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                                .build()
                            PhoneAuthProvider.verifyPhoneNumber(options)
                        }else{
//                            Toast.makeText(this,"please Enter correct no",Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }

        })*/


        signup_otpVerifyBT.setOnClickListener {


            otpCode = signup_otp.getText().toString()
            if (otpCode.isNotEmpty()) {
                if (otpCode.length == 6){
                    credential = PhoneAuthProvider.getCredential(verifyOTP, otpCode)
                    vOTP = verifyOTP
                    Log.d("OTPOTP",verifyOTP+""+otpCode)
                    if (vOTP == verifyOTP)  {
//                        Toast.makeText(this@MainActivity, "Please enter correct 1 OTP", Toast.LENGTH_SHORT).show()
                    /*    progressBar.visibility = View.VISIBLE
                        loginOtp.visibility = View.GONE*/
                        signInWithPhoneAuthCredential(credential)
                    }
                    else{
                        Toast.makeText(this@SignUpActivity, "Please enter correct OTP", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@SignUpActivity, "Please enter 6 digits OTP", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@SignUpActivity, "Please enter the OTP", Toast.LENGTH_SHORT).show()
            }

//             startActivity(Intent(this,HomeScreen::class.java))



        }

    }

    override fun initializeInputs() {

    }

    override fun initializeLabels() {

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Global.language(this,resources)
        setLayoutXml()
        initializeViews()
        initializeClickListners()
        initializeInputs()
        initializeLabels()


    }

    private fun showDatePicker() {
        val date = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateLabel()
        }

        val datePickerDialog = DatePickerDialog(
            this@SignUpActivity,
            R.style.MyDatePickerDialogTheme, // use your custom theme here
            date,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )

        // Set the maximum and minimum date for the DatePickerDialog
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.datePicker.minDate = getMinimumDate()

        datePickerDialog.show()
    }

    private fun updateLabel() {
        val myFormat = "yyyy/MM/dd"
        val formattedDate = myFormat.replace("/", "-")
        val dateFormat = SimpleDateFormat(formattedDate, Locale.US)
        dob.setText(dateFormat.format(myCalendar.time))
    }

    private fun getMinimumDate(): Long {
        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.add(Calendar.YEAR, -100) // Set 100 years ago from now
        return minDateCalendar.timeInMillis
    }

    private val callbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d("TAG","verificationFailed ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG","FirebaseTooManyRequestsException ${e.toString()}")
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
            Log.d(SignUpActivity.TAG, "onCodeSent:$verificationId")
           /* loginOtp.visibility = View.VISIBLE
            login.visibility = View.VISIBLE
            signup_otpVerifyBT.visibility = View.GONE
            resend_otp.visibility = View.VISIBLE*/
//            progressBar.visibility = View.GONE
            verifyOTP =  verificationId
            vOTP =  verificationId
//            resentToken = token


            // Save verification ID and resending token so we can use them later
            /*       storedVerificationId = verificationId
                   resendToken = token*/
        }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser != null){
            startActivity(Intent(this@SignUpActivity,HomeScreen::class.java))
        }
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                   /* startActivity(Intent(this@SignUpActivity, HomeScreen::class.java))
                    finish()*/
                    signup_otpLL.visibility = View.GONE
                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI

                }

            }
    }

    fun checkSignUp(){
        /*   if(phoneNumber == "")
           {
               Toast.makeText(this,"Please Enter Name",Toast.LENGTH_SHORT).show()
           }else{*/
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val userService  = retrofit.create(SignupApi::class.java)
        val parameters = mapOf(
            "mobileNo" to phoneNumber,
            "name" to full_name,
            "dob" to signup_dob)
        val call = userService.addUser(parameters)
        call.enqueue(object : Callback<SignUpInfo> {
            override fun onResponse(call: Call<SignUpInfo>, response: Response<SignUpInfo>) {
                if (response.isSuccessful) {
                    val STATUSCODE = response.body()?.status
                    val createdUser = response.body()
                    Log.d("ResponseSignup ",createdUser.toString())
                    // Handle the created user object
//                    showErrorDialog("successful","OK")
                    if (response.body()?.status?.toInt() != 501) {
                       tkn = response.body()?.token.toString()
                        prefManager.setToken(tkn)
                        Log.d("tkn","$tkn")
                        if (validateName(sName) && validateNumber(sPhone) && validateDob(dob)) {
                            Log.d("STATUSCODE","$STATUSCODE")
                            Handler().postDelayed({
                                // After 4 seconds
                                loadingDialog.dismissDialog()
                            startActivity(Intent(this@SignUpActivity, DocumentStatus::class.java))
                            }, 4000) // 4 seconds

                        }
                    }
                } else {
                    // Handle the error response
//                    Log.d("ElseSignup ","t.toString()")
                    val errorResponseCode = response.code()
                    val errorResponseBody = response.errorBody()?.string()
                    // Handle the error response code and body
                    Log.e("API Error", "Response Code: $errorResponseCode, Body: $errorResponseBody")
                    // Show a generic error message to the user

//                    showErrorDialog("An error occurred. Please try again later.","OK")

                    if (errorResponseCode == 422) {
                        try {
                            val errorJson = JSONObject(errorResponseBody)
                            val errorMessage = errorJson.getString("msg")
                            val errorsJson = errorJson.getJSONObject("errors")
                            val nameErrors = errorsJson.getJSONArray("name")
                            val dobErrors = errorsJson.getJSONArray("dob")

                            val nameError = if (nameErrors.length() > 0) nameErrors.getString(0) else null
                            val dobError = if (dobErrors.length() > 0) dobErrors.getString(0) else null

                            // Handle the specific validation errors
                            if (nameError != null) {
                                // Display name validation error
                                showErrorDialog(nameError,"")
                            }
                            if (dobError != null) {
                                // Display dob validation error

                                showErrorDialog(dobError,"")
                            }
                        } catch (e: JSONException) {
                            // Handle JSON parsing error
                            Log.e("API Error", "JSON parsing error", e)
                            // Show a generic error message to the user
                            showErrorDialog("An error occurred. Please try again later.","OK")
                        }
                    } else {
                        // Show a generic error message to the user
                        Log.d("errormsg","${response.code()}")
                        showErrorDialog("An else error occurred. Please try again later.","Ok")
                    }
                }
            }

            override fun onFailure(call: Call<SignUpInfo>, t: Throwable) {
                Log.d("ErrorSignUp ",t.toString())
                // Handle the network error
                if (t is IOException) {
                    // Network error occurred
                    Log.e("API Error", "Network error occurred", t)
                    // Show a generic network error message to the user
                    showErrorDialog("Network error. Please check your internet connection.","Ok")
                } else {
                    // Other types of errors occurred
                    Log.e("API Error", "Error occurred", t)
                    // Show a generic error message to the user
                    showErrorDialog("An error occurred. Please try again later.","OK")
                }

            }
        })

    }

}
