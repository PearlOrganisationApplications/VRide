package com.pearl.v_ride

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Resources
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.textfield.TextInputLayout
import com.pearl.common.retrofit.data_model_class.ResponseData
import com.pearl.common.retrofit.data_model_class.ServiceRequestData
import com.pearl.common.retrofit.rest_api_interface.ServiceApi
import com.pearl.v_ride.databinding.ActivityIssueRequestBinding
import com.pearl.v_ride_lib.Dialog
import com.pearl.v_ride_lib.GPSBroadcastReceiver


import com.pearl.v_ride_lib.Global
import com.pearl.v_ride_lib.Global.baseUrl
import com.pearl.v_ride_lib.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IssueRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIssueRequestBinding
//    lateinit var customToolbar: ConstraintLayout
    private lateinit var resourcess : Resources
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle:AppCompatTextView
    lateinit var clickIV: ImageView
    lateinit var setImageIV: ImageView
    lateinit var requestTV: TextView
    lateinit var descriptionET: EditText
    lateinit var subjectET: EditText
    lateinit var subjectTIL: TextInputLayout
    lateinit var issue_submit: Button
    lateinit var prefManager: PrefManager
    private lateinit var loadingDialog: Dialog
    var subject = ""
    var description = ""
    private val gpsBroadcastReceiver = GPSBroadcastReceiver()
    private val filter = IntentFilter().apply {
        addAction(LocationManager.PROVIDERS_CHANGED_ACTION)
        addAction(ConnectivityManager.CONNECTIVITY_ACTION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager = PrefManager(this)
        loadingDialog = Dialog(this)
        setContentView(R.layout.activity_issue_request)
        resourcess = Global.language(this,resources)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_issue_request)

//        customToolbar = findViewById(R.id.customToolbar)
        ivback=findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)
        clickIV = findViewById(R.id.clickIV)
        setImageIV = findViewById(R.id.setImageIV)
        requestTV = findViewById(R.id.requestTV)
        subjectTIL = findViewById(R.id.subjectTIL)
        issue_submit = findViewById(R.id.issue_submit)
        descriptionET = findViewById(R.id.descriptionET)
        subjectET = findViewById(R.id.subjectET)
        apptitle.setText(R.string.service_request)
        ivback.setOnClickListener {
            onBackPressed()
        }
        issue_submit.setOnClickListener {
            subject = subjectET.text.toString()
            description = descriptionET.text.toString()
            loadingDialog.startLoadingDialog()
            issueRequest()
        }
        clickIV.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

    }

    override fun onResume() {
        super.onResume()

        apptitle.text = resourcess.getString(R.string.service_request)
        requestTV.text = resourcess.getString(R.string.request_form)
        subjectTIL.hint = resourcess.getString(R.string.service_request)
        descriptionET.hint = resourcess.getString(R.string.issue_description)


        registerReceiver(gpsBroadcastReceiver, filter)
//        gpsBroadcastReceiver.showNetworkDisconnectedDialog(this)
    }

    fun issueRequest() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requestData = ServiceRequestData(
            request_name = subject,
            description = description
        )

        val token = prefManager.getToken()

        val apiService = retrofit.create(ServiceApi::class.java)
        val call = apiService.sendRequest("Bearer $token", requestData)

        call.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null && responseData.status == "true") {
                        // Request sent successfully
                        val message = responseData.msg
                        Log.d("msg", message)
                        // Handle the success case
                        Handler().postDelayed({
                            // After 4 seconds
                            subjectET.text.clear()
                            descriptionET.text.clear()
                            loadingDialog.dismissDialog()

                        }, 4000) // 4 seconds
                    } else {
                        // Request failed or response body is null
                        // Handle the failure case
                        Log.d("elseFail","$response")
                    }
                } else {
                    // Request failed
                    // Handle the failure case
                    val errorResponseCode = response.code()
                    val errorResponseBody = response.errorBody()?.string()
                    // Handle the error response code and body
                    Log.e("API Error", "Response Code: $errorResponseCode, Body: $errorResponseBody")
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                // Handle network errors or request failure
               Log.d("failure", "${t.toString()} ${t.printStackTrace()}")
            }
        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            setImageIV.setImageURI(uri)
        }else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()

        unregisterReceiver(gpsBroadcastReceiver)
    }

}