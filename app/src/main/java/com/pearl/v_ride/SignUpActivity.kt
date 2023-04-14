package com.pearl.v_ride

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import com.pearl.test5.R
import com.pearl.ui.DocumentActivity
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity() {

    lateinit var dob: EditText
    lateinit var signup: Button
    private val myCalendar = Calendar.getInstance()
    lateinit var verify: Button
    lateinit var cancel: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signup = findViewById(R.id.signupBtn)
        val register = findViewById<TextView>(R.id.already)
        dob= findViewById(R.id.signup_dob)
        val dialog = Dialog(this)

        dob.setOnClickListener {
            showDatePicker()
        }

        register.setOnClickListener {
            startActivity(Intent(this@SignUpActivity,MainActivity::class.java))
        }
        signup.setOnClickListener {
//            startActivity(Intent(this@SignUpActivity,ForgotPasswordActivity::class.java))
//
                dialog.setContentView(R.layout.activity_forgot_password)
                dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.setCancelable(false)
                dialog.window?.attributes?.windowAnimations = R.style.animation

                verify = dialog.findViewById(R.id.otp_Verify_button)
                cancel = dialog.findViewById(R.id.view_cancel_dialog)

                verify.setOnClickListener {
                    dialog.dismiss()
                    Toast.makeText(this, "okay clicked", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignUpActivity,DocumentActivity::class.java))
                }

                cancel.setOnClickListener {
                    dialog.dismiss()
                    Toast.makeText(this@SignUpActivity, "Cancel clicked", Toast.LENGTH_SHORT).show()
                }

                dialog.show()
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
