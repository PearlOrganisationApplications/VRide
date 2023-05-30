package com.pearl.v_ride

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.pearl.v_ride_lib.Global
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pearl.common.retrofit.data_model_class.ProfileData
import com.pearl.common.retrofit.rest_api_interface.ProfileApi

import com.pearl.v_ride_lib.BaseClass
import com.pearl.v_ride_lib.PrefManager
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : BaseClass() {

    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var dobET: TextInputEditText
    lateinit var adharNoTIT: TextInputEditText
    lateinit var fullNameET: TextInputEditText
    lateinit var mobileNoEt: TextInputEditText
    lateinit var panNoTIT: TextInputEditText
    private val myCalendar = Calendar.getInstance()
    lateinit var selfiee: CircleImageView
    lateinit var edtProfile: Button
    lateinit var update_profileBT: Button
    private lateinit var dialog: Dialog
    lateinit var moblie_no: TextInputLayout
    lateinit var email_id: TextInputLayout
    lateinit var aadharCardTL: TextInputLayout
    lateinit var pancardTL: TextInputLayout
    lateinit var dobTIL: TextInputLayout
    lateinit var nameTL: TextInputLayout
    lateinit var update_profile: TextView
    lateinit var verify: Button
    lateinit var cancel: ImageView
    private lateinit var resourcess : Resources
    lateinit var prefManager: PrefManager
//    lateinit var emaiTIL:TextInputLayout
    override fun setLayoutXml() {
        setContentView(R.layout.activity_profile)
    }

    override fun initializeViews() {
        prefManager = PrefManager(this)
        resourcess = Global.language(this,resources)
        ivback=findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)
        selfiee = findViewById(R.id.show_selfiee)
        dobET= findViewById(R.id.dobTL)
        apptitle.setText(R.string.profile)
        edtProfile = findViewById(R.id.editprofile)
        update_profileBT = findViewById(R.id.updateButton)
        moblie_no = findViewById(R.id.mobileTL)
        email_id = findViewById(R.id.emailTL)
        aadharCardTL = findViewById(R.id.aadharCardTL)
        pancardTL = findViewById(R.id.pancardTL)
        dobTIL = findViewById(R.id.dobTIL)
        nameTL = findViewById(R.id.nameTL)
        update_profile = findViewById(R.id.update_profile)
        adharNoTIT = findViewById(R.id.adharNo)
        fullNameET = findViewById(R.id.fullNameET)
        mobileNoEt = findViewById(R.id.mobileNoEt)
        panNoTIT = findViewById(R.id.panNoTIT)
//        emaiTIL = findViewById(R.id.emailTIL)
         dialog = Dialog(this)
    }

    override fun initializeClickListners() {
        ivback.setOnClickListener {
            onBackPressed()
        }
       /* dob.setOnClickListener {
            showDatePicker()
        }*/

        edtProfile.setOnClickListener {
            update_profileBT.visibility = View.VISIBLE
            edtProfile.visibility = View.GONE
            moblie_no.isEnabled = true
//            moblie_no.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,getDrawable(R.drawable.ic_baseline_lock)), null)
            email_id.isEnabled = true

            selfiee.setOnClickListener {
                ImagePicker.with(this)
                    .crop()
                    .cameraOnly()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start()

            }

        }

        update_profileBT.setOnClickListener {
            moblie_no.isEnabled = false
            email_id.isEnabled = false

            dialog.setContentView(R.layout.activity_forgot_password)
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.setCancelable(false)
            dialog.window?.attributes?.windowAnimations = R.style.animation

            verify = dialog.findViewById(R.id.otp_Verify_button)
            cancel = dialog.findViewById(R.id.view_cancel_dialog)

            verify.setOnClickListener {
                dialog.dismiss()
                Toast.makeText(this@ProfileActivity, "okay clicked", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@ProfileActivity,HomeScreen::class.java))
            }

            cancel.setOnClickListener {
                dialog.dismiss()
                Toast.makeText(this@ProfileActivity, "Cancel clicked", Toast.LENGTH_SHORT).show()
            }
            dialog.show()

            edtProfile.visibility = View.VISIBLE
            update_profileBT.visibility = View.GONE


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
        getDocStatus()

    }

    override fun onResume() {
        super.onResume()
        apptitle.text = resourcess.getString(R.string.profile)
        edtProfile.text = resourcess.getString(R.string.edit_profile)
        update_profileBT.text = resourcess.getString(R.string.update_profile)
        update_profile.text = resourcess.getString(R.string.update_profile)
        nameTL.hint = resourcess.getString(R.string.full_name)
        dobTIL.hint = resourcess.getString(R.string.dob)
        pancardTL.hint = resourcess.getString(R.string.pan_card)
        aadharCardTL.hint = resourcess.getString(R.string.aadhar_card)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            selfiee.setImageURI(uri)
//            Global.imageString = uri
            Global.imageString = uri.toString()
            Log.d("abc2", Global.imageString)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDatePicker() {
        val date = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateLabel()
        }

        val datePickerDialog = DatePickerDialog(
            this@ProfileActivity,
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
        val myFormat = "yyyy/MM/DD"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        dobET.setText(dateFormat.format(myCalendar.time))
    }

    private fun getMinimumDate(): Long {
        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.add(Calendar.YEAR, -100) // Set 100 years ago from now
        return minDateCalendar.timeInMillis
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
        call.enqueue(object : Callback<ProfileData> {
            override fun onResponse(call: Call<ProfileData>, response: Response<ProfileData>) {
                if (response.isSuccessful) {
                    val profileData = response.body()
                    if (profileData != null) {
                        val profile = profileData.profileData
                        val message = profileData.message
                        val profilePicUrl = profile?.profilePic
                        val mobile = profile?.mobile
                        val name = profile?.name
                        val dob = profile?.dob
                        val adharNo = profile?.adharNo
                        val otherDetails = profileData.otherDetails
                        val panNo = otherDetails?.panNo

                        adharNoTIT.setText(adharNo.toString())
                        dobET.setText(dob)
                        fullNameET.setText(name)
                        mobileNoEt.setText(mobile)
                        update_profile.text = name
                        panNoTIT.setText(panNo)





                        Picasso.get().load(profilePicUrl).placeholder(R.drawable.profile).into(selfiee)

                        Log.d("profile", ""+profilePicUrl)
                        Log.d("profile", "$profile $otherDetails")
                        Log.d("msg", "$message")
//                        showErrorDialog("$message","ok")

                        // Use the profile data as needed

                    }  else {
                        Log.d("ElseSignup ","t.toString()")
                        val errorResponseCode = response.code()
                        val errorResponseBody = response.errorBody()?.string()
                        // Handle the error response code and body
                        Log.e("API Error", "Response Code: $errorResponseCode, Body: $errorResponseBody")
                    }
                }
            }

            override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                Log.d("fail",t.toString())
            }

        })



    }
}