package com.pearl.v_ride

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.pearl.test5.R
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class DocumentActivity : AppCompatActivity() {

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
    lateinit var select_merchant: Spinner
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

    /*  lateinit var imageUri: Uri
      private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()){
          adharFrontIV.setImageURI(null)
          adharFrontIV.setImageURI(imageUri)
      }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document)

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

        doc_profile = findViewById(R.id.doc_selfie)
        add_selfie = findViewById(R.id.add_selfie)
        select_merchant = findViewById(R.id.merchantSL)
        selfieCor = findViewById(R.id.corporate_doc_selfie)
        addCorporateSelfie = findViewById(R.id.add_corporate_selfie)
        companyID = findViewById(R.id.corporate_docID)
        corporateAadharF = findViewById(R.id.corporate_adharFrotIV)
        corporateAadharR = findViewById(R.id.corporate_adharrearIV)
        addCorporateAadharF = findViewById(R.id.corporate_addfront)
        addCorporateAadharR = findViewById(R.id.corporate_addrear)

        val items = arrayOf("Select Merchant", "Amazon","Flipcard","Zomato","Rapido","Uber","Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        select_merchant.adapter = adapter

        pan_dob = findViewById(R.id.pan_dobET)
        pan_dob.setOnClickListener {
            req_code =1
            showDatePicker()
        }
        doc_dob = findViewById(R.id.doc_dob)
        doc_dob.setOnClickListener {
            req_code = 2
            showDatePicker()
        }
        updateBT = findViewById<Button>(R.id.updateBT)

        ivback = findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)


        apptitle.text = "Document"
        ivback.setOnClickListener {
            onBackPressed()
        }


        // camera
//        imageUri = createImageUri()!!
        adhadharF.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
             image_type = 1

//                .galleryOnly()
//            contract.launch(imageUri)
        }
        adhadharR.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
            image_type = 2
        }
        addPan.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
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
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
            image_type = 6
        }
        addCorporateSelfie.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
            image_type = 7
        }
        companyID.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
            image_type = 8
        }
        addCorporateAadharF.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
            image_type = 9
        }
        addCorporateAadharR.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
            image_type = 10
        }

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
        } else if (image_type == 2){
            adhadharRear.setImageURI(uri)
        }else if (image_type == 3){
            panFront.setImageURI(uri)
        }else if (image_type == 4){
            passbookIV.setImageURI(uri)
        } else if (image_type == 5){
            licenceIV.setImageURI(uri)
        }else if (image_type == 6){
            doc_profile.setImageURI(uri)
        }else if (image_type == 7){
            selfieCor.setImageURI(uri)
        }else if (image_type == 8){
            companyID.setImageURI(uri)
        }
        else if (image_type == 9) {
            corporateAadharF.setImageURI(uri)
        }else if (image_type == 10){
            corporateAadharR.setImageURI(uri)
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
        }else if (req_code == 2){
            doc_dob.setText(dateFormat.format(myCalendar.time))

        }
    }

    private fun getMinimumDate(): Long {
        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.add(Calendar.YEAR, -100) // Set 100 years ago from now
        return minDateCalendar.timeInMillis
    }
}