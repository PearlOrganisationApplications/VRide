package com.pearl.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pearl.v_ride.HomeScreen
import com.pearl.v_ride.R
import com.pearl.v_ride_lib.BaseClass
import com.pearl.v_ride_lib.Global
import com.pearl.v_ride_lib.PrefManager
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class DocumentStatus : BaseClass() {
    private lateinit var doc_selfieCL: ConstraintLayout
    private lateinit var doc_constraintLayout: ConstraintLayout
    private lateinit var doc_addressLL: LinearLayout
    private lateinit var doc_merchantLL: LinearLayout
    private lateinit var doc_pancardCL: ConstraintLayout
    private lateinit var doc_bankCl: ConstraintLayout
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
    private lateinit var doc_merchantOK: ImageView
    private lateinit var doc_merchantError: ImageView
    private lateinit var doc_merchant_showMore: ImageView
    private lateinit var doc_merchant_showLess: ImageView
    private lateinit var doc_panOK: ImageView
    private lateinit var doc_panError: ImageView
    private lateinit var doc_pan_showMore: ImageView
    private lateinit var doc_pan_showLess: ImageView
    private lateinit var doc_bankOk: ImageView
    private lateinit var doc_bankError: ImageView
    private lateinit var doc_bank_showMore: ImageView
    private lateinit var doc_bank_showLess: ImageView
    private lateinit var okBT: Button

    private lateinit var items: Array<String>
    lateinit var doc_select_state: Spinner
    lateinit var doc_profile: CircleImageView
    lateinit var doc_add_selfie: ImageView
    lateinit var doc_pan_dob: EditText
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView

    lateinit var pan_dob: EditText
    private val myCalendar = Calendar.getInstance()
    lateinit var adhadharF: ImageView
    lateinit var adhadharR: ImageView
    lateinit var adharFrontIV: ImageView
    lateinit var adhadharRear: ImageView
    var image_type by Delegates.notNull<Int>()
    lateinit var addPan: ImageView
    lateinit var panFront: ImageView
    lateinit var addPassbook: ImageView
    lateinit var passbookIV: ImageView
    lateinit var yesBT: Button
    lateinit var noBT: Button
    lateinit var merchantWorkingLL: LinearLayout
    lateinit var checkboxLL: LinearLayout
    lateinit var merchantAlready: LinearLayout
    lateinit var submitAlreadyBT: Button
    lateinit var addressProofIV: ImageView
    lateinit var addressProofTV: TextView
    lateinit var listLayout: ConstraintLayout
    lateinit var merchantList: ListView
    lateinit var addFAB: FloatingActionButton
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dataList: MutableList<String>
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var resourcess: Resources
    private lateinit var aadhar_n_front: TextView
    private lateinit var aadhar_n_rear: TextView
    private lateinit var address: TextView
    private lateinit var city: TextView
    private lateinit var pin_code: TextView
    private lateinit var merchant: TextView
    private lateinit var enter_all_merchant_name_you_are_working_with: TextView
    private lateinit var enter_merchant_id: TextView
    private lateinit var enter_merchant_name: TextView
    private lateinit var pancardTV: TextView
    private lateinit var passbookTV: TextView
    private lateinit var panNoET: EditText
    private lateinit var panNameET: EditText
    private lateinit var bNameET: EditText
    private lateinit var accountNOET: EditText
    private lateinit var raccountNOET: EditText
    private lateinit var ifscCode: EditText
    private lateinit var doc_selfieUpdateBT: Button
    private var req_code = 1


    override fun setLayoutXml() {
        setContentView(R.layout.activity_document_status)
    }

    override fun initializeViews() {
//        inner Layouts
        doc_selfieCL = findViewById(R.id.doc_selfieCL)
        doc_constraintLayout = findViewById(R.id.doc_constraintLayout)
        doc_addressLL = findViewById(R.id.doc_addressLL)
        doc_merchantLL = findViewById(R.id.doc_merchantLL)
        doc_pancardCL = findViewById(R.id.doc_pancardCL)
        doc_bankCl = findViewById(R.id.doc_bankCl)
//        selfie card
        doc_selfieOK = findViewById(R.id.doc_selfieOK)
        doc_selfieError = findViewById(R.id.doc_selfieError)
        doc_selfie_showMore = findViewById(R.id.doc_selfie_showMore)
        doc_selfie_showLess = findViewById(R.id.doc_selfie_showLess)
        doc_selfieUpdateBT = findViewById(R.id.doc_selfieUpdateBT)
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
//        merchant
        doc_merchantOK = findViewById(R.id.doc_merchantOK)
        doc_merchantError = findViewById(R.id.doc_merchantError)
        doc_merchant_showMore = findViewById(R.id.doc_merchant_showMore)
        doc_merchant_showLess = findViewById(R.id.doc_merchant_showLess)
//        pancard
        doc_panOK = findViewById(R.id.doc_panOK)
        doc_panError = findViewById(R.id.doc_panError)
        doc_pan_showMore = findViewById(R.id.doc_pan_showMore)
        doc_pan_showLess = findViewById(R.id.doc_pan_showLess)
        pan_dob = findViewById(R.id.doc_pan_dobET)
//        bankDetails
        doc_bankOk = findViewById(R.id.doc_bankOk)
        doc_bankError = findViewById(R.id.doc_bankError)
        doc_bank_showMore = findViewById(R.id.doc_bank_showMore)
        doc_bank_showLess = findViewById(R.id.doc_bank_showLess)

        okBT = findViewById(R.id.okBT)
        doc_select_state = findViewById(R.id.doc_statelistSP)
        doc_profile = findViewById(R.id.doc_doc_selfie)
        doc_add_selfie = findViewById(R.id.doc_add_selfie)
        doc_pan_dob = findViewById(R.id.doc_pan_dobET)

        addFAB = findViewById(R.id.doc_addFAB)
        merchantList = findViewById(R.id.doc_merchantList)
        listLayout = findViewById(R.id.doc_listLayout)
        editText1 = findViewById(R.id.doc_editText1)
        editText2 = findViewById(R.id.doc_editText2)


        aadhar_n_front = findViewById(R.id.doc_aadhar_n_front)
        aadhar_n_rear = findViewById(R.id.doc_aadhar_n_rear)
        address = findViewById(R.id.doc_address)
        city = findViewById(R.id.doc_city)
        pin_code = findViewById(R.id.doc_pin_code)

        pancardTV = findViewById(R.id.doc_pancardTV)
        panNoET = findViewById(R.id.doc_panNoET)
        panNameET = findViewById(R.id.doc_panNameET)

        passbookTV = findViewById(R.id.doc_passbookTV)
        bNameET = findViewById(R.id.doc_bNameET)
        accountNOET = findViewById(R.id.doc_accountNOET)
        raccountNOET = findViewById(R.id.doc_raccountNOET)
        ifscCode = findViewById(R.id.doc_ifscCode)

        merchant = findViewById(R.id.doc_merchant)
        enter_all_merchant_name_you_are_working_with =
            findViewById(R.id.doc_enter_all_merchant_name_you_are_working_with)
        enter_merchant_id = findViewById(R.id.doc_enter_merchant_id)
        enter_merchant_name = findViewById(R.id.doc_enter_merchant_name)

        addressProofIV = findViewById(R.id.doc_addressProofIV)
        addressProofTV = findViewById(R.id.doc_addressProofTV)
        submitAlreadyBT = findViewById(R.id.doc_submitAlreadyBT)

        resourcess = Global.language(this, resources)
        /* merchantanotherWorkingLL = findViewById(R.id.doc_merchantanotherWorkingLL)
           anotherYesBT = findViewById(R.id.doc_merchantAYes)
           anotherNoBT = findViewById(R.id.doc_merchantANo) */

        merchantAlready = findViewById(R.id.doc_merchantAlready)
        checkboxLL = findViewById(R.id.doc_checkboxLL)
        merchantWorkingLL = findViewById(R.id.doc_merchantWorkingLL)
        yesBT = findViewById(R.id.doc_merchantYes)
        noBT = findViewById(R.id.doc_merchantNo)
        adhadharF = findViewById(R.id.doc_addfront)
        adharFrontIV = findViewById(R.id.doc_adharFrotIV)
        adhadharRear = findViewById(R.id.doc_adharrearIV)
        adhadharR = findViewById(R.id.doc_addrear)
        addPan = findViewById(R.id.doc_add_pan_cam)
        panFront = findViewById(R.id.doc_pan_frontIV)
        addPassbook = findViewById(R.id.doc_add_passbook_cam)
        passbookIV = findViewById(R.id.doc_bankPassbookIV)
        apptitle = findViewById(R.id.titleTVAppbar)
        ivback = findViewById(R.id.ivBack)

        apptitle.setText(R.string.document)

        dataList = mutableListOf()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList)
        merchantList.adapter = adapter
        
    }

    override fun initializeClickListners() {

        if (doc_selfieError.isVisible){
            doc_selfie_showMore.setOnClickListener {
                doc_selfieCL.visibility = View.VISIBLE
                doc_selfie_showLess.visibility = View.VISIBLE
                doc_selfie_showMore.visibility = View.GONE
            }
            doc_selfie_showLess.setOnClickListener {
                doc_selfieCL.visibility = View.GONE
                doc_selfie_showLess.visibility = View.GONE
                doc_selfie_showMore.visibility = View.VISIBLE
            }
            doc_selfieUpdateBT.setOnClickListener {
                doc_selfieCL.visibility = View.GONE
                doc_selfieError.visibility = View.GONE
                doc_selfieOK.visibility = View.VISIBLE
                doc_selfie_showLess.visibility = View.GONE
                doc_selfie_showMore.visibility = View.VISIBLE
            }

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

        if (doc_merchantError.isVisible){
            doc_merchant_showMore.setOnClickListener {
                doc_merchantLL.visibility = View.VISIBLE
                doc_merchant_showLess.visibility = View.VISIBLE
                doc_merchant_showMore.visibility = View.GONE
            }
        }
        doc_merchant_showLess.setOnClickListener {
            doc_merchantLL.visibility = View.GONE
            doc_merchant_showLess.visibility = View.GONE
            doc_merchant_showMore.visibility = View.VISIBLE
        }

        if (doc_panError.isVisible) {
            doc_pan_showMore.setOnClickListener {
                doc_pancardCL.visibility = View.VISIBLE
                doc_pan_showLess.visibility = View.VISIBLE
                doc_pan_showMore.visibility = View.GONE
            }
        }
        doc_pan_showLess.setOnClickListener {
            doc_pancardCL.visibility = View.GONE
            doc_pan_showLess.visibility = View.GONE
            doc_pan_showMore.visibility = View.VISIBLE
        }

        if (doc_bankError.isVisible) {
            doc_bank_showMore.setOnClickListener {
                doc_bankCl.visibility = View.VISIBLE
                doc_bank_showLess.visibility = View.VISIBLE
                doc_bank_showMore.visibility = View.GONE
            }
        }
        doc_bank_showLess.setOnClickListener {
            doc_bankCl.visibility = View.GONE
            doc_bank_showLess.visibility = View.GONE
            doc_bank_showMore.visibility = View.VISIBLE
        }

        okBT.setOnClickListener {
            startActivity(Intent(this@DocumentStatus,HomeScreen::class.java))
            finish()
        }

        submitAlreadyBT.setOnClickListener {
            listLayout.visibility = View.VISIBLE
            merchantAlready.visibility = View.GONE

            val text1 = editText1.text.toString().trim()
            val text2 = editText2.text.toString().trim()

            if (text1.isNotEmpty() && text2.isNotEmpty()) {
                val combinedText = "$text1 $text2"
                dataList.add(combinedText)
                adapter.notifyDataSetChanged()

                editText1.text.clear()
                editText2.text.clear()
            }
        }

        /*anotherYesBT.setOnClickListener {
            merchantAlready.visibility = View.VISIBLE
            merchantanotherWorkingLL.visibility = View.GONE
        }
        anotherNoBT.setOnClickListener {

        }*/
        addFAB.setOnClickListener {
            listLayout.visibility = View.GONE
            merchantAlready.visibility = View.VISIBLE
        }
        yesBT.setOnClickListener {
            merchantAlready.visibility = View.VISIBLE
            merchantWorkingLL.visibility = View.GONE

        }
        noBT.setOnClickListener {
            merchantWorkingLL.visibility = View.GONE
            checkboxLL.visibility = View.VISIBLE

        }
        pan_dob.setOnClickListener {
            req_code = 1
            showDatePicker()
        }


        ivback.setOnClickListener {
            onBackPressed()
        }
        // camera
//        imageUri = createImageUri()!!
        adhadharF.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)                    //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
            image_type = 1

//                .galleryOnly()
//            contract.launch(imageUri)
        }
        adhadharR.setOnClickListener {
            ImagePicker.with(this)
                .crop()                            //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
            image_type = 2
        }
        addPan.setOnClickListener {
            ImagePicker.with(this)
                .crop()                      // Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
            image_type = 3
        }
        addPassbook.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
            image_type = 4
        }

        doc_add_selfie.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .cameraOnly()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
            image_type = 6
        }
        addressProofTV.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)                    //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
            image_type = 11
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

        var prefManager = PrefManager(this)
        items = arrayOf("S")
        if (prefManager.getLanID().equals("en"))
            items = arrayOf(
                "Select State",
                "Andhra Pradesh",
                "Arunachal Pradesh",
                "Assam",
                "Bihar",
                "Chhattisgarh",
                "Goa",
                "Gujarat",
                "Haryana",
                "Himachal Pradesh",
                "Jammu and Kashmir",
                "Jharkhand",
                "Karnataka",
                "Kerala",
                "Madhya Pradesh",
                "Maharashtra",
                "Manipur",
                "Meghalaya",
                "Mizoram",
                "Nagaland",
                "Odisha",
                "Punjab",
                "Sikkim",
                "Tamil Nadu",
                "Telangana",
                "Tripura",
                "Uttar Pradesh",
                "Uttarakhand",
                "West Bengal"
            )
        else
            items = arrayOf(
                "राज्य चुनें",
                "आंध्र प्रदेश",
                "अरुणाचल प्रदेश",
                "असम",
                "बिहार",
                "छत्तीसगढ़",
                "गोवा",
                "गुजरात",
                "हरियाणा",
                "हिमाचल प्रदेश",
                "जम्मू और कश्मीर",
                "झारखंड",
                "कर्नाटक",
                "केरल",
                "मध्य प्रदेश",
                "महाराष्ट्र",
                "मणिपुर",
                "मेघालय",
                "मिजोरम",
                "नागालैंड",
                "ओडिशा",
                "पंजाब",
                "सिक्किम",
                "तमिलनाडु",
                "तेलंगाना",
                "त्रिपुरा",
                "उत्तर प्रदेश",
                "उत्तराखंड",
                "पश्चिम बंगाल"
            )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        doc_select_state.adapter = adapter


    }

    private fun showDatePicker() {
        val date = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateLabel()
        }
        val datePickerDialog = DatePickerDialog(
            this@DocumentStatus,
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
        if (req_code == 1) {
            pan_dob.setText(dateFormat.format(myCalendar.time))
        }
        /*else if (req_code == 2){
                        doc_dob.setText(dateFormat.format(myCalendar.time))
            }*/
    }

    private fun getMinimumDate(): Long {
        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.add(Calendar.YEAR, -100) //Set 100 years ago from now
        return minDateCalendar.timeInMillis

    }
}