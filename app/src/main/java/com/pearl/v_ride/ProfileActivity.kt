package com.pearl.v_ride

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.pearl.v_ride_lib.Global
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

import com.pearl.test5.R
import com.pearl.v_ride_lib.BaseClass
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : BaseClass() {

    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var dob: TextInputEditText
    private val myCalendar = Calendar.getInstance()
    lateinit var selfiee: CircleImageView
    lateinit var edtProfile: Button
    lateinit var update_profile: Button
    private lateinit var dialog: Dialog
    lateinit var moblie_no: TextInputLayout
    lateinit var email_id: TextInputLayout
    lateinit var verify: Button
    lateinit var cancel: ImageView
//    lateinit var emaiTIL:TextInputLayout
    override fun setLayoutXml() {
        setContentView(R.layout.activity_profile)
    }

    override fun initializeViews() {
        ivback=findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)
        selfiee = findViewById(R.id.show_selfiee)
        dob= findViewById(R.id.dobTL)
        apptitle.text ="Profile"
        edtProfile = findViewById(R.id.editprofile)
        update_profile = findViewById(R.id.updateButton)
        moblie_no = findViewById(R.id.mobileTL)
        email_id = findViewById(R.id.emailTL)
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
            update_profile.visibility = View.VISIBLE
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

        update_profile.setOnClickListener {
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
            update_profile.visibility = View.GONE


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
        val myFormat = "MM/dd/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        dob.setText(dateFormat.format(myCalendar.time))
    }

    private fun getMinimumDate(): Long {
        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.add(Calendar.YEAR, -100) // Set 100 years ago from now
        return minDateCalendar.timeInMillis
    }
}