package com.pearl.v_ride

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputLayout
import com.pearl.test5.R
import com.pearl.test5.databinding.ActivityIssueRequestBinding
import com.pearl.v_ride_lib.Global

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
    lateinit var subjectTIL: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        descriptionET = findViewById(R.id.descriptionET)
        apptitle.setText(R.string.service_request)
        ivback.setOnClickListener {
            onBackPressed()
        }
        clickIV.setOnClickListener {

        }

    }

    override fun onResume() {
        super.onResume()

        apptitle.text = resourcess.getString(R.string.service_request)
        requestTV.text = resourcess.getString(R.string.request_form)
        subjectTIL.hint = resourcess.getString(R.string.service_request)
        descriptionET.hint = resourcess.getString(R.string.issue_description)

    }

}