package com.pearl.v_ride

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.pearl.common.retrofit.data_model_class.ProfileData
import com.pearl.common.retrofit.rest_api_interface.ProfileApi
import com.pearl.v_ride_lib.BaseClass
import com.pearl.v_ride_lib.Global
import com.pearl.v_ride_lib.PrefManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException

class VerificationActivity : BaseClass() {
    lateinit var number: LinearLayout
    lateinit var email: LinearLayout
    lateinit var exit: Button
    lateinit var prefManager: PrefManager
    lateinit var tv2: TextView
    lateinit var waiting_layout: CardView
    var name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLayoutXml()
        initializeViews()
        initializeClickListners()
        initializeInputs()
        initializeLabels()
        var intent_call = Intent(Intent.ACTION_DIAL)
        exit.setOnClickListener {
            val startMain = Intent(Intent.ACTION_MAIN)
            startMain.addCategory(Intent.CATEGORY_HOME)
            startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(startMain)
        }
        getDocStatus()

        prefManager.setCode(0)
//          var name:String = pref.getDriverName()

//        prefManager.setRegistrationToken("Done")

    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_verification)
        supportActionBar?.height
        prefManager = PrefManager(this)
    }

    override fun initializeViews() {

        number = findViewById<LinearLayout>(R.id.call_us)
        email = findViewById<LinearLayout>(R.id.email_us)
        exit = findViewById<Button>(R.id.exit)
        tv2 = findViewById<TextView>(R.id.textView2)
//          val args = arguments
        waiting_layout = findViewById<CardView>(R.id.waiting_cardview)

    }

    override fun initializeClickListners() {

    }

    override fun initializeInputs() {
    }

    override fun initializeLabels() {
    }

    fun getDocStatus() {

        val retrofit = Retrofit.Builder()
                .baseUrl(Global.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
//            .client(createOkHttpClient())
                .build()


        val profileService = retrofit.create(ProfileApi::class.java)


//                val response = profileApi.getProfileData()

        val token = prefManager.getToken()
        val call = profileService.getProfileData("Bearer $token")
        Log.d("Bearer token",token)
        call.enqueue(object : retrofit2.Callback<ProfileData> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: retrofit2.Call<ProfileData>, response: retrofit2.Response<ProfileData>) {
                if (response.isSuccessful) {
                    val profileData = response.body()
                    if (profileData != null) {
                        val profile = profileData.profileData
                        val message = profileData.message
                        val profileName = profile?.name


                        name = profileName.toString()

                        tv2.text = "Hello $name\n" +
                                "You registered an account on V-run App .Your Document Verification is under process, we will connect with you shortly"
//                        Picasso.get().load(profilePicUrl).placeholder(R.drawable.profile).into(dImage)

                        Log.d("profile", ""+profileName)
                        Log.d("msg", "$message")
//                        showErrorDialog("$message","ok")

                        // Use the profile data as needed

                    }  else {
                        Log.d("ElseSignup ","t.toString()")
                        val errorResponseCode = response.code()
                        val errorResponseBody = response.errorBody()?.string()
                        // Handle the error response code and body
                        Log.e("API Error3", "Response Code: $errorResponseCode, Body: $errorResponseBody")
                        Log.d("responserror","${response.body()?.message} ${response.body()?.status}")
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<ProfileData>, t: Throwable) {
                when (t) {
                    is SocketTimeoutException -> {
                        // Handle SocketTimeoutException
                        Log.d("fail1", "Socket Timeout: ${t.message}")
                        val errorMessage = "Socket Timeout: ${t.message}"
                        showErrorToast(errorMessage)
                    }
                    is IOException -> {
                        // Handle IOException
                        Log.d("fail", "IO Exception: ${t.message}")
                        val errorMessage = "IO Exception: ${t.message}"
                        showErrorToast(errorMessage)
                    }
                    else -> {
                        // Handle other types of exceptions or generic error
                        val errorMessage = "Error: ${t.message}"
                        Log.d("fail3", "Error: ${t.message}")
                        showErrorToast(errorMessage)
                    }
                }
            }

        })



    }
}