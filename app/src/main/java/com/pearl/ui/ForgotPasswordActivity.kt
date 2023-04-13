package com.pearl.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.pearl.test5.R
import com.pearl.v_ride_lib.BaseClass

class ForgotPasswordActivity : BaseClass() {
    lateinit var otpVerifyBtn: Button
    override fun setLayoutXml() {
        setContentView(R.layout.activity_forgot_password)
    }

    override fun initializeViews() {
      otpVerifyBtn = findViewById(R.id.otp_Verify_button)
    }

    override fun initializeClickListners() {
        otpVerifyBtn.setOnClickListener {
            startActivity(Intent(this,DocumentActivity::class.java))
        }
    }

    override fun initializeInputs() {

    }

    override fun initializeLabels() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutXml()
        initializeViews()
        initializeClickListners()
        initializeInputs()
        initializeLabels()

    }
}