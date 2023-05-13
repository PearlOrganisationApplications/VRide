package com.pearl.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
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
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class DocumentActivity : BaseClass() {

    lateinit var pan_dob: EditText
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var updateBT: Button
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
    lateinit var addDL: ImageView
    lateinit var licenceIV: ImageView
    lateinit var select_state: Spinner
    lateinit var doc_profile: CircleImageView
    lateinit var add_selfie: ImageView
    lateinit var doc_dob: EditText
    private var req_code = 1
    lateinit var selfieCor: CircleImageView
    lateinit var addCorporateSelfie: ImageView
    lateinit var companyID: ImageView
    lateinit var corporateAadharF: ImageView
    lateinit var corporateAadharR: ImageView
    lateinit var addCorporateAadharF: ImageView
    lateinit var addCorporateAadharR: ImageView
    lateinit var docLL: LinearLayout
    lateinit var corporateLL: LinearLayout
    lateinit var persnalIV: ImageView
    lateinit var corporateIV: ImageView
    lateinit var personalTV: TextView
    lateinit var corporateTV: TextView
    lateinit var merchantanotherWorkingLL: LinearLayout
    lateinit var anotherYesBT: Button
    lateinit var anotherNoBT: Button
    lateinit var adharNoEdt: EditText
    lateinit var updateBT2: Button
    lateinit var yesBT: Button
    lateinit var noBT: Button
    lateinit var merchantWorkingLL: LinearLayout
    lateinit var checkboxLL: LinearLayout
    lateinit var merchantAlready: LinearLayout
    lateinit var layout1LL: LinearLayout
    lateinit var layout2LL: LinearLayout
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
    private lateinit var your_selfie: TextView
    private lateinit var selfie: TextView
    private lateinit var note: TextView
    private lateinit var aadhar_card: TextView
    private lateinit var aadhar_n_front: TextView
    private lateinit var aadhar_n_rear: TextView
    private lateinit var current_address: TextView
    private lateinit var address: TextView
    private lateinit var city: TextView
    private lateinit var pin_code: TextView
    private lateinit var merchant_mapping: TextView
    private lateinit var merchant: TextView
    private lateinit var enter_all_merchant_name_you_are_working_with: TextView
    private lateinit var enter_merchant_id: TextView
    private lateinit var enter_merchant_name: TextView
    private lateinit var pan_card: TextView
    private lateinit var statusP: TextView
    private lateinit var pancardTV: TextView
    private lateinit var bank_details: TextView
    private lateinit var statusB: TextView
    private lateinit var passbookTV: TextView
    private lateinit var driving_licence: TextView
    private lateinit var statusD: TextView
    private lateinit var licenceTV: TextView
    private lateinit var panNoET: EditText
    private lateinit var panNameET: EditText
    private lateinit var bNameET: EditText
    private lateinit var accountNOET: EditText
    private lateinit var raccountNOET: EditText
    private lateinit var ifscCode: EditText
    private lateinit var licenceNoET: EditText
    private lateinit var items: Array<String>

    override fun setLayoutXml() {
        setContentView(R.layout.activity_document)
    }

    override fun initializeViews() {


        layout1LL = findViewById(R.id.layout1LL)
        layout2LL = findViewById(R.id.layout2LL)
        updateBT2 = findViewById(R.id.updateBT2)
        addFAB = findViewById(R.id.addFAB)
        merchantList = findViewById(R.id.merchantList)
        listLayout = findViewById(R.id.listLayout)
        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)

        your_selfie = findViewById(R.id.your_selfie)
        selfie = findViewById(R.id.selfie)
        note = findViewById(R.id.note)
        aadhar_card = findViewById(R.id.aadhar_card)
        aadhar_n_front = findViewById(R.id.aadhar_n_front)
        aadhar_n_rear = findViewById(R.id.aadhar_n_rear)
        current_address = findViewById(R.id.current_address)
        address = findViewById(R.id.address)
        city = findViewById(R.id.city)
        pin_code = findViewById(R.id.pin_code)

        pan_card = findViewById(R.id.pan_card)
        statusP = findViewById(R.id.statusP)
        pancardTV = findViewById(R.id.pancardTV)
        panNoET = findViewById(R.id.panNoET)
        panNameET = findViewById(R.id.panNameET)
        bank_details = findViewById(R.id.bank_details)

        statusB = findViewById(R.id.statusB)

        passbookTV = findViewById(R.id.passbookTV)
        bNameET = findViewById(R.id.bNameET)
        accountNOET = findViewById(R.id.accountNOET)
        raccountNOET = findViewById(R.id.raccountNOET)
        ifscCode = findViewById(R.id.ifscCode)
        driving_licence = findViewById(R.id.driving_licence)
        statusD = findViewById(R.id.statusD)
        licenceTV = findViewById(R.id.licenceTV)
        licenceNoET = findViewById(R.id.licenceNoET)

        merchant_mapping = findViewById(R.id.merchant_mapping)
        merchant = findViewById(R.id.merchant)
        enter_all_merchant_name_you_are_working_with =
            findViewById(R.id.enter_all_merchant_name_you_are_working_with)
        enter_merchant_id = findViewById(R.id.enter_merchant_id)
        enter_merchant_name = findViewById(R.id.enter_merchant_name)

        addressProofIV = findViewById(R.id.addressProofIV)
        addressProofTV = findViewById(R.id.addressProofTV)
        submitAlreadyBT = findViewById(R.id.submitAlreadyBT)

        resourcess = Global.language(this, resources)
        /* merchantanotherWorkingLL = findViewById(R.id.merchantanotherWorkingLL)
           anotherYesBT = findViewById(R.id.merchantAYes)
           anotherNoBT = findViewById(R.id.merchantANo) */

        merchantAlready = findViewById(R.id.merchantAlready)
        checkboxLL = findViewById(R.id.checkboxLL)
        merchantWorkingLL = findViewById(R.id.merchantWorkingLL)
        yesBT = findViewById(R.id.merchantYes)
        noBT = findViewById(R.id.merchantNo)
        adhadharF = findViewById(R.id.addfront)
        adharFrontIV = findViewById(R.id.adharFrotIV)
        adhadharRear = findViewById(R.id.adharrearIV)
        adhadharR = findViewById(R.id.addrear)
        addPan = findViewById(R.id.add_pan_cam)
        panFront = findViewById(R.id.pan_frontIV)
        addPassbook = findViewById(R.id.add_passbook_cam)
        passbookIV = findViewById(R.id.bankPassbookIV)
        addDL = findViewById(R.id.add_dl_cam)
        licenceIV = findViewById(R.id.licenceIV)
        adharNoEdt = findViewById(R.id.adharNoET)

        doc_profile = findViewById(R.id.doc_selfie)
        add_selfie = findViewById(R.id.add_selfie)
        select_state = findViewById(R.id.statelistSP)
        /* selfieCor = findViewById(R.id.corporate_doc_selfie)
         addCorporateSelfie = findViewById(R.id.add_corporate_selfie)
         companyID = findViewById(R.id.corporate_docID)
         corporateAadharF = findViewById(R.id.corporate_adharFrotIV)
         corporateAadharR = findViewById(R.id.corporate_adharrearIV)
         addCorporateAadharF = findViewById(R.id.corporate_addfront)
         addCorporateAadharR = findViewById(R.id.corporate_addrear)
         docLL = findViewById(R.id.docLL)
         corporateLL = findViewById(R.id.corporateLL)
         doc_dob = findViewById(R.id.doc_dob)
         persnalIV = findViewById(R.id.personalIV)
         corporateIV = findViewById(R.id.corporateIV)
         personalTV = findViewById(R.id.personalTV)
         corporateTV = findViewById(R.id.corporateTV)*/
        pan_dob = findViewById(R.id.pan_dobET)

        updateBT = findViewById<Button>(R.id.updateBT)
        ivback = findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)
        apptitle.setText(R.string.document)

        dataList = mutableListOf()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList)
        merchantList.adapter = adapter
    }

    override fun initializeClickListners() {

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
        /* doc_dob.setOnClickListener {
             req_code = 2
             showDatePicker()
         }*/

        updateBT2.setOnClickListener {
            startActivity(Intent(this@DocumentActivity, HomeScreen::class.java))
            finish()
        }
        updateBT.setOnClickListener {
            layout1LL.visibility = View.GONE
            layout2LL.visibility = View.VISIBLE
            updateBT.visibility = View.GONE
            updateBT2.visibility = View.VISIBLE
        }
        /* persnalIV.setOnClickListener {
             docLL.visibility =View.VISIBLE
             corporateLL.visibility = View.GONE
 //            req_code =3

         }*/
        /*corporateIV.setOnClickListener {
            docLL.visibility = View.GONE
            corporateLL.visibility = View.VISIBLE
//            req_code = 4
            *//*   if ( docLL.visibility == View.GONE){
                   corporateTV.setTextColor(Color.parseColor("#096E0A"))
               }*//*
        }*/
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
        addDL.setOnClickListener {
            ImagePicker.with(this)
//                .crop()
                .cropSquare()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
            image_type = 5
        }
        add_selfie.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .cameraOnly()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
            image_type = 6
        }
        /*addCorporateSelfie.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .cameraOnly()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
            image_type = 7
        }*/
        /*companyID.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
            image_type = 8
        }*/
        /* addCorporateAadharF.setOnClickListener {
             ImagePicker.with(this)
                 .crop()
                 .compress(1024)
                 .maxResultSize(1080, 1080)
                 .start()
             image_type = 9
         }*/
        /*  addCorporateAadharR.setOnClickListener {
              ImagePicker.with(this)
                  .crop()
                  .compress(1024)
                  .maxResultSize(1080, 1080)
                  .start()
              image_type = 10
          }*/
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

    /*  lateinit var imageUri: Uri
      private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()){
          adharFrontIV.setImageURI(null)
          adharFrontIV.setImageURI(imageUri)
      }*/

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var Resources = Global.language(this, resources)

        setLayoutXml()
        initializeViews()
        initializeClickListners()
        initializeInputs()
        initializeLabels()

//        validateAadharNo(adharNoEdt)

        val otherEdt = findViewById<EditText>(R.id.otherET)

        val submitMerchantBtn = findViewById<Button>(R.id.submitMerchantBT)
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
        select_state.adapter = adapter


        /*      if(select_merchant.selectedItem.toString().equals("Other")){
                  otherEdt.visibility = View.VISIBLE
                  Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()
              }*/


        /*select_merchant.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == 6){
                    otherEdt.visibility = View.VISIBLE
                    submitMerchantBtn.visibility = View.VISIBLE
                }else{
                    otherEdt.visibility = View.GONE
                    submitMerchantBtn.visibility = View.GONE

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

                Toast.makeText(this@DocumentActivity, "hi", Toast.LENGTH_SHORT).show()
            }
        }*/


        /*updateBT.setOnClickListener {
            docLL.visibility = View.GONE
            corporateLL.visibility = View.VISIBLE
        }*/
        /*      if (req_code == 3){
                  personalTV.setTextColor(Color.parseColor("#096E0A"))
                  Toast.makeText(this, "personal", Toast.LENGTH_SHORT).show()
              }
              else if (req_code == 4){
                  Toast.makeText(this, "personal2", Toast.LENGTH_SHORT).show()
                  corporateTV.setTextColor(Color.parseColor("#096E0A"))
              }*/

        /*    if ( docLL.visibility == View.VISIBLE){
                personalTV.setTextColor(Color.parseColor("#096E0A"))
                corporateTV.setTextColor(Color.parseColor("#000000"))
            }else if (corporateLL.visibility == View.VISIBLE){
                corporateTV.setTextColor(Color.parseColor("#096E0A"))
                personalTV.setTextColor(Color.parseColor("#000000"))
            }*/

    }


    /*    private fun createImageUri(): Uri? {

            val image = File(applicationContext.filesDir,"camera_photos.png")
            return FileProvider.getUriForFile(applicationContext,
                "com.pearl.v_ride.fileProvider",image)
        }*/
/*    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    val uri = data?.data
    adharFrontIV.setImageURI(uri)
    }*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions

            if (image_type == 1) {
                adharFrontIV.setImageURI(uri)
            } else if (image_type == 2) {
                adhadharRear.setImageURI(uri)
            } else if (image_type == 3) {
                panFront.setImageURI(uri)
            } else if (image_type == 4) {
                passbookIV.setImageURI(uri)
            } else if (image_type == 5) {
                licenceIV.setImageURI(uri)
            } else if (image_type == 6) {
                doc_profile.setImageURI(uri)
            }/*else if (image_type == 7){
                selfieCor.setImageURI(uri)
            }else if (image_type == 8){
                companyID.setImageURI(uri)
            }
            else if (image_type == 9) {
                corporateAadharF.setImageURI(uri)
            }else if (image_type == 10){
                corporateAadharR.setImageURI(uri)
            }*/
            else if (image_type == 11) {
                addressProofIV.setImageURI(uri)
            }


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
            this@DocumentActivity,
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

    override fun onResume() {
        super.onResume()
        adharNoEdt.hint = resourcess.getString(R.string.aadhar_card_no)
        address.hint = resourcess.getString(R.string.address)
        city.hint = resourcess.getString(R.string.city)
        pin_code.hint = resourcess.getString(R.string.pin_code)
        your_selfie.text = resourcess.getString(R.string.your_selfie)
        selfie.text = resourcess.getString(R.string.selfie)
        note.text = resourcess.getString(R.string.note)
        aadhar_card.text = resourcess.getString(R.string.aadhar_card)
        aadhar_n_front.text = resourcess.getString(R.string.aadhar_n_front)
        aadhar_n_rear.text = resourcess.getString(R.string.aadhar_n_rear)
        current_address.text = resourcess.getString(R.string.current_address)
        addressProofTV.text = resourcess.getString(R.string.address_proof)
        apptitle.text = resourcess.getString(R.string.document)

        merchant_mapping.text = resourcess.getString(R.string.merchant_mapping)
        merchant.text = resourcess.getString(R.string.merchant)
        enter_all_merchant_name_you_are_working_with.text =
            resourcess.getString(R.string.enter_all_merchant_name_you_are_working_with)
        enter_merchant_id.text = resourcess.getString(R.string.enter_merchant_id)
        enter_merchant_name.text = resourcess.getString(R.string.enter_merchant_name)
        submitAlreadyBT.text = resourcess.getString(R.string.save)
        yesBT.text = resourcess.getString(R.string.yes)
        noBT.text = resourcess.getString(R.string.no)
        editText1.hint = resourcess.getString(R.string.merchant_name)
        editText2.hint = resourcess.getString(R.string.merchant_id)
        pan_dob.hint = resourcess.getString(R.string.dd_mm_yy)
        panNoET.hint = resourcess.getString(R.string.pan_no)
        panNameET.hint = resourcess.getString(R.string.name_as_pan)
        bNameET.hint = resourcess.getString(R.string.bank_name)
        accountNOET.hint = resourcess.getString(R.string.please_enter_your_account_no)
        raccountNOET.hint = resourcess.getString(R.string.renter_your_account_no)
        ifscCode.hint = resourcess.getString(R.string.ifsc_code)
        licenceNoET.hint = resourcess.getString(R.string.dl_no)
        pan_card.text = resourcess.getString(R.string.pan_card)
        statusP.text = resourcess.getString(R.string.status)
        pancardTV.text = resourcess.getString(R.string.pan_card)
        bank_details.text = resourcess.getString(R.string.bank_details)
        statusB.text = resourcess.getString(R.string.status)
        passbookTV.text = resourcess.getString(R.string.bank_n_details)
        driving_licence.text = resourcess.getString(R.string.driving_licence)
        licenceTV.text = resourcess.getString(R.string.driving_n_licence)


    }
}