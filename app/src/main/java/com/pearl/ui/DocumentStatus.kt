package com.pearl.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import com.pearl.v_ride.R
import com.pearl.v_ride_lib.BaseClass

class DocumentStatus : BaseClass() {
    private lateinit var doc_selfieCL: ConstraintLayout
    private lateinit var doc_constraintLayout: ConstraintLayout
    private lateinit var doc_addressLL: LinearLayout
    private lateinit var doc_selfieOK: ImageView
    private lateinit var doc_selfieError: ImageView
    private lateinit var doc_selfie_showMore: ImageView
    private lateinit var doc_selfie_showLess: ImageView
    private lateinit var doc_aadharOK: ImageView
    private lateinit var doc_aadharError: ImageView
    private lateinit var doc_aadhar_showMore: ImageView
    private lateinit var doc_aadhar_showLess: ImageView
    private lateinit var doc_addressOK: ImageView
    private lateinit var doc_addressError: ImageView
    private lateinit var doc_address_showMore: ImageView
    private lateinit var doc_address_showLess: ImageView

    override fun setLayoutXml() {
        setContentView(R.layout.activity_document_status)
    }

    override fun initializeViews() {
//        inner Layouts
        doc_selfieCL = findViewById(R.id.doc_selfieCL)
        doc_constraintLayout = findViewById(R.id.doc_constraintLayout)
        doc_addressLL = findViewById(R.id.doc_addressLL)
//        selfie card
        doc_selfieOK = findViewById(R.id.doc_selfieOK)
        doc_selfieError = findViewById(R.id.doc_selfieError)
        doc_selfie_showMore = findViewById(R.id.doc_selfie_showMore)
        doc_selfie_showLess = findViewById(R.id.doc_selfie_showLess)
//        aadharCard
        doc_aadharOK = findViewById(R.id.doc_aadharOK)
        doc_aadharError = findViewById(R.id.doc_aadharError)
        doc_aadhar_showMore = findViewById(R.id.doc_aadhar_showMore)
        doc_aadhar_showLess = findViewById(R.id.doc_aadhar_showLess)
//        address
        doc_addressOK = findViewById(R.id.doc_addressOK)
        doc_addressError = findViewById(R.id.doc_addressError)
        doc_address_showMore = findViewById(R.id.doc_address_showMore)
        doc_address_showLess = findViewById(R.id.doc_address_showLess)
    }

    override fun initializeClickListners() {

        if (doc_selfieError.isVisible){
            doc_selfie_showMore.setOnClickListener {
                doc_selfieCL.visibility = View.VISIBLE
                doc_selfie_showLess.visibility = View.VISIBLE
                doc_selfie_showMore.visibility = View.GONE
            }
        }
        doc_selfie_showLess.setOnClickListener {
            doc_selfieCL.visibility = View.GONE
            doc_selfie_showLess.visibility = View.GONE
            doc_selfie_showMore.visibility = View.VISIBLE
        }

        if (doc_aadharError.isVisible) {
            doc_aadhar_showMore.setOnClickListener {
                doc_constraintLayout.visibility = View.VISIBLE
                doc_aadhar_showLess.visibility = View.VISIBLE
                doc_aadhar_showMore.visibility = View.GONE
            }
        }
        doc_aadhar_showLess.setOnClickListener {
            doc_constraintLayout.visibility = View.GONE
            doc_aadhar_showLess.visibility = View.GONE
            doc_aadhar_showMore.visibility = View.VISIBLE
        }

        if (doc_addressError.isVisible) {
            doc_address_showMore.setOnClickListener {
                doc_addressLL.visibility = View.VISIBLE
                doc_address_showLess.visibility = View.VISIBLE
                doc_address_showMore.visibility = View.GONE
            }
        }
        doc_address_showLess.setOnClickListener {
            doc_addressLL.visibility = View.GONE
            doc_address_showLess.visibility = View.GONE
            doc_address_showMore.visibility = View.VISIBLE
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