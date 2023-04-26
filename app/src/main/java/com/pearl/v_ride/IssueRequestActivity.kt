package com.pearl.v_ride

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import com.pearl.test5.R
import com.pearl.test5.databinding.ActivityIssueRequestBinding

class IssueRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIssueRequestBinding
//    lateinit var customToolbar: ConstraintLayout
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle:AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_request)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_issue_request)

//        customToolbar = findViewById(R.id.customToolbar)
        ivback=findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)

        apptitle.text ="Issue Report"
        ivback.setOnClickListener {
            onBackPressed()
        }

    }

}