package com.pearl.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.pearl.test5.R
import com.pearl.v_ride.HomeScreen
import com.pearl.v_ride_lib.BaseClass
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
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
    lateinit var adharNoEdt: EditText

    override fun setLayoutXml() {
        setContentView(R.layout.activity_document)
    }

    override fun initializeViews() {
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
        selfieCor = findViewById(R.id.corporate_doc_selfie)
        addCorporateSelfie = findViewById(R.id.add_corporate_selfie)
        companyID = findViewById(R.id.corporate_docID)
        corporateAadharF = findViewById(R.id.corporate_adharFrotIV)
        corporateAadharR = findViewById(R.id.corporate_adharrearIV)
        addCorporateAadharF = findViewById(R.id.corporate_addfront)
        addCorporateAadharR = findViewById(R.id.corporate_addrear)
        docLL = findViewById(R.id.docLL)
        corporateLL = findViewById(R.id.corporateLL)

        persnalIV = findViewById(R.id.personalIV)
        corporateIV = findViewById(R.id.corporateIV)
        personalTV = findViewById(R.id.personalTV)
        corporateTV = findViewById(R.id.corporateTV)
        pan_dob = findViewById(R.id.pan_dobET)
        doc_dob = findViewById(R.id.doc_dob)
        updateBT = findViewById<Button>(R.id.updateBT)
        ivback = findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)
        apptitle.text = "Document"
    }

    override fun initializeClickListners() {
        pan_dob.setOnClickListener {
            req_code =1
            showDatePicker()
        }
        doc_dob.setOnClickListener {
            req_code = 2
            showDatePicker()
        }

        updateBT.setOnClickListener {
            startActivity(Intent(this@DocumentActivity,HomeScreen::class.java))
            finish()
        }
        persnalIV.setOnClickListener {
            docLL.visibility =View.VISIBLE
            corporateLL.visibility = View.GONE
//            req_code =3

        }
        corporateIV.setOnClickListener {
            docLL.visibility = View.GONE
            corporateLL.visibility = View.VISIBLE
//            req_code = 4
            /*   if ( docLL.visibility == View.GONE){
                   corporateTV.setTextColor(Color.parseColor("#096E0A"))
               }*/
        }
        ivback.setOnClickListener {
            onBackPressed()
        }
        // camera
//        imageUri = createImageUri()!!
        adhadharF.setOnClickListener {
            ImagePicker.with(this)
                .crop()
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
                .cameraOnly()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
            image_type = 6
        }
        addCorporateSelfie.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .cameraOnly()
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

        setLayoutXml()
        initializeViews()
        initializeClickListners()
        initializeInputs()
        initializeLabels()
        validateAadharNo(adharNoEdt)

        val otherEdt = findViewById<EditText>(R.id.otherET)

        val submitMerchantBtn = findViewById<Button>(R.id.submitMerchantBT)

        val items = arrayOf("Select State","Andhra Pradesh", "Arunachal Pradesh","Assam", "Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand",
            "Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal")
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

        if ( docLL.visibility == View.VISIBLE){
            personalTV.setTextColor(Color.parseColor("#096E0A"))
            corporateTV.setTextColor(Color.parseColor("#000000"))
        }else if (corporateLL.visibility == View.VISIBLE){
            corporateTV.setTextColor(Color.parseColor("#096E0A"))
            personalTV.setTextColor(Color.parseColor("#000000"))
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