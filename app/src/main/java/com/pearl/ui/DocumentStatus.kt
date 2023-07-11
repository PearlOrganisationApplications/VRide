package com.pearl.ui


import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pearl.adapter.CheckboxAdapter
import com.pearl.common.retrofit.data_model_class.*
import com.pearl.common.retrofit.rest_api_interface.*
import com.pearl.v_ride_lib.Dialog
import com.pearl.v_ride.HomeScreen
import com.pearl.v_ride.R
import com.pearl.v_ride.VerificationActivity
import com.pearl.v_ride_lib.BaseClass
import com.pearl.v_ride_lib.Global
import com.pearl.v_ride_lib.PrefManager
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class DocumentStatus : BaseClass() {
    lateinit var prefManager: PrefManager
    val baseUrl = "https://test.pearl-developer.com/vrun/public/"
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
    private lateinit var loadingDialog: Dialog

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
    private lateinit var doc_address: EditText
    private lateinit var doc_city: EditText
    private lateinit var doc_landmark: EditText
    private lateinit var pin_code: EditText
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
    private lateinit var ifsc_code: EditText
    private lateinit var doc_adharNoET: EditText
    private lateinit var doc_selfieUpdateBT: Button
    private lateinit var doc_submitAdharBT: Button
    private lateinit var doc_submitPanBT: Button
    private lateinit var doc_updateAddressBT: Button
    private lateinit var doc_submitBankBt: Button
    private lateinit var doc_submitMerchantBT: Button
    private lateinit var doc_selfieeLL: LinearLayout
    private lateinit var doc_aadharLL: LinearLayout
    private lateinit var doc_adrsLL: LinearLayout
    private lateinit var doc_merchantLayout: LinearLayout
    private lateinit var doc_pancardLL: LinearLayout
    private lateinit var doc_bankLL: LinearLayout
    private var req_code = 0
    var b64: String = ""
    var adharNo: String = ""
    var panName: String = ""
    var panNo: String = ""
    var selectedItem: String = ""
    var bName: String = ""
    var bAcount: String = ""
    var bRAcount: String = ""
    var bifsc: String = ""
    var city: String = ""
    var landmark: String = ""
    var addrass: String = ""
    var pinCode: String = ""
    var isSelfie = false
    var isAdharcard = false
    var isPancard = false
    var isAddress = false
    var isBank = false
    var isMerchant = false
    lateinit var checkBoxRV: RecyclerView
    private val listCard = ArrayList<Merchant>()
    val adapterCheckBox = CheckboxAdapter(this, listCard)
    val selectedMerchantIds = mutableListOf<Int>()
    lateinit var checkBox: CheckBox
    val merchantIds = listOf<String>()
    var list = ""
    var isAadharVisible = false
    var isSelfieVisible = false
    var isAddressVisible = false
    var isMerchantVisible = false
    var isBankVisible = false
    var isPancardVisible = false

    override fun onResume() {
        super.onResume()
        registerReceiver(gpsBroadcastReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(gpsBroadcastReceiver)
    }
    override fun setLayoutXml() {
        setContentView(R.layout.activity_document_status)
        prefManager = PrefManager(this)
        checkBox = CheckBox(this@DocumentStatus)
        getDocStatus()
        if (isSelfie || isAdharcard || isAddress || isMerchant || isPancard || isBank) {
            Log.d("isSelfie", isSelfie.toString())
            prefManager.setCode(0)
        }
    }

    override fun initializeViews() {

        loadingDialog = Dialog(this)
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
        doc_selfieeLL = findViewById(R.id.doc_selfieeLL)
//        aadharCard
        doc_aadharOK = findViewById(R.id.doc_aadharOK)
        doc_aadharError = findViewById(R.id.doc_aadharError)
        doc_aadhar_showMore = findViewById(R.id.doc_aadhar_showMore)
        doc_aadhar_showLess = findViewById(R.id.doc_aadhar_showLess)
        doc_adharNoET = findViewById(R.id.doc_adharNoET)
        doc_submitAdharBT = findViewById(R.id.doc_submitAdharBT)
        doc_aadharLL = findViewById(R.id.doc_aadharLL)
//        address
        doc_addressOK = findViewById(R.id.doc_addressOK)
        doc_addressError = findViewById(R.id.doc_addressError)
        doc_address_showMore = findViewById(R.id.doc_address_showMore)
        doc_address_showLess = findViewById(R.id.doc_address_showLess)
        doc_address = findViewById(R.id.doc_address)
        doc_city = findViewById(R.id.doc_city)
        doc_landmark = findViewById(R.id.doc_landmark)
        pin_code = findViewById(R.id.doc_pin_code)
        doc_updateAddressBT = findViewById(R.id.doc_updateAddressBT)
        doc_adrsLL = findViewById(R.id.doc_adrsLL)

//        merchant
        doc_merchantOK = findViewById(R.id.doc_merchantOK)
        doc_merchantError = findViewById(R.id.doc_merchantError)
        doc_merchant_showMore = findViewById(R.id.doc_merchant_showMore)
        doc_merchant_showLess = findViewById(R.id.doc_merchant_showLess)
        doc_submitMerchantBT = findViewById(R.id.doc_submitMerchantBT)
        doc_merchantLayout = findViewById(R.id.doc_merchantLayout)
//        pancard
        doc_panOK = findViewById(R.id.doc_panOK)
        doc_panError = findViewById(R.id.doc_panError)
        doc_pan_showMore = findViewById(R.id.doc_pan_showMore)
        doc_pan_showLess = findViewById(R.id.doc_pan_showLess)
        pancardTV = findViewById(R.id.doc_pancardTV)
        panNoET = findViewById(R.id.doc_panNoET)
        panNameET = findViewById(R.id.doc_panNameET)
        doc_submitPanBT = findViewById(R.id.doc_submitPanBT)
        doc_pancardLL = findViewById(R.id.doc_pancardLL)
//        pan_dob = findViewById(R.id.doc_pan_dobET)
//        bankDetails
        doc_bankOk = findViewById(R.id.doc_bankOk)
        doc_bankError = findViewById(R.id.doc_bankError)
        doc_bank_showMore = findViewById(R.id.doc_bank_showMore)
        doc_bank_showLess = findViewById(R.id.doc_bank_showLess)
        passbookTV = findViewById(R.id.doc_passbookTV)
        bNameET = findViewById(R.id.doc_bNameET)
        accountNOET = findViewById(R.id.doc_accountNOET)
        raccountNOET = findViewById(R.id.doc_raccountNOET)
        ifsc_code = findViewById(R.id.doc_ifscCode)
        doc_submitBankBt = findViewById(R.id.doc_submitBankBt)
        doc_bankLL = findViewById(R.id.doc_bankLL)

        okBT = findViewById(R.id.okBT)
        doc_select_state = findViewById(R.id.doc_statelistSP)
        doc_profile = findViewById(R.id.doc_doc_selfie)
        doc_add_selfie = findViewById(R.id.doc_add_selfie)
//        doc_pan_dob = findViewById(R.id.doc_pan_dobET)

        addFAB = findViewById(R.id.doc_addFAB)
        merchantList = findViewById(R.id.doc_merchantList)
        listLayout = findViewById(R.id.doc_listLayout)
        editText1 = findViewById(R.id.doc_editText1)
        editText2 = findViewById(R.id.doc_editText2)


        aadhar_n_front = findViewById(R.id.doc_aadhar_n_front)
        aadhar_n_rear = findViewById(R.id.doc_aadhar_n_rear)
        checkBoxRV = findViewById(R.id.checkBoxRV)



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

        items = arrayOf("S")
        if (prefManager.getLanID() == "en")
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

    override fun initializeClickListners() {

        doc_select_state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItem = parent.getItemAtPosition(position).toString()


                Log.d("selectedItem", selectedItem)
                // Do something with the selected item

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle the case when nothing is selected
            }
        }


        if (doc_selfieError.isVisible) {
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
            doc_selfieeLL.setOnClickListener {
                if (isSelfieVisible) {
                    doc_selfieCL.visibility = View.GONE
                    doc_selfie_showLess.visibility = View.GONE
                    doc_selfie_showMore.visibility = View.VISIBLE
                } else {
                    doc_selfieCL.visibility = View.VISIBLE
                    doc_selfie_showLess.visibility = View.VISIBLE
                    doc_selfie_showMore.visibility = View.GONE
                }
                isSelfieVisible = !isSelfieVisible
            }


            doc_selfieUpdateBT.setOnClickListener {

//                Log.d("image","$CODE")

                if (req_code == 1) {
                    loadingDialog.startLoadingDialog()
                    selfieDetails()
                } else {
                    showErrorToast("please upload your photo first ")
                }


            }
        }

        if (doc_aadharError.isVisible) {
            doc_aadhar_showMore.setOnClickListener {
                /*doc_constraintLayout.visibility = View.VISIBLE
                doc_aadhar_showLess.visibility = View.VISIBLE
                doc_aadhar_showMore.visibility = View.GONE*/
                adharOpen()
            }
        }
        doc_aadhar_showLess.setOnClickListener {
            /*doc_constraintLayout.visibility = View.GONE
            doc_aadhar_showLess.visibility = View.GONE
            doc_aadhar_showMore.visibility = View.VISIBLE*/
            adharClose()
        }
        doc_aadharLL.setOnClickListener {
            if (isAadharVisible) {
                adharClose()
            } else {
                adharOpen()
            }
            isAadharVisible = !isAadharVisible
        }
        doc_submitAdharBT.setOnClickListener {
            buttonEffect(doc_submitAdharBT)
            adharNo = doc_adharNoET.text.toString().trim()

            loadingDialog.startLoadingDialog()
            if (validateAadharNo(doc_adharNoET) && req_code == 3) {
                adharDetails()
            } else {
                Handler(Looper.myLooper()!!).postDelayed({
                    loadingDialog.dismissDialog()
                    Toast.makeText(
                        this@DocumentStatus,
                        "please fill all details",
                        Toast.LENGTH_SHORT
                    ).show()
                }, 2000)

            }

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
        doc_adrsLL.setOnClickListener {
            if (isAddressVisible) {
                doc_addressLL.visibility = View.GONE
                doc_address_showLess.visibility = View.GONE
                doc_address_showMore.visibility = View.VISIBLE
            } else {
                doc_addressLL.visibility = View.VISIBLE
                doc_address_showLess.visibility = View.VISIBLE
                doc_address_showMore.visibility = View.GONE
            }
            isAddressVisible = !isAddressVisible
        }
        doc_updateAddressBT.setOnClickListener {
            city = doc_city.text.toString()
            addrass = doc_address.text.toString()
            pinCode = pin_code.text.toString()
            landmark = doc_landmark.text.toString()

            loadingDialog.startLoadingDialog()
            Log.d("selectedItem1", selectedItem)
            if (validateAddress1(doc_address) && validateCity(doc_city) && validateLandnark(doc_landmark) && validatePincode(pin_code) && selectedItem.isNotEmpty()
                && req_code == 6
            ) {
                addressDetails()
            } else {
                Handler(Looper.myLooper()!!).postDelayed({
                    loadingDialog.dismissDialog()
                    Toast.makeText(
                        this@DocumentStatus,
                        "please fill all details",
                        Toast.LENGTH_SHORT
                    ).show()
                }, 2000)

            }

        }

        if (doc_merchantError.isVisible) {
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
        doc_merchantLayout.setOnClickListener {
            if (isMerchantVisible) {
                doc_merchantLL.visibility = View.GONE
                doc_merchant_showLess.visibility = View.GONE
                doc_merchant_showMore.visibility = View.VISIBLE
            } else {
                doc_merchantLL.visibility = View.VISIBLE
                doc_merchant_showLess.visibility = View.VISIBLE
                doc_merchant_showMore.visibility = View.GONE
            }
            isMerchantVisible = !isMerchantVisible
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
        doc_pancardLL.setOnClickListener {
            if (isPancardVisible) {
                doc_pancardCL.visibility = View.GONE
                doc_pan_showLess.visibility = View.GONE
                doc_pan_showMore.visibility = View.VISIBLE
            } else {
                doc_pancardCL.visibility = View.VISIBLE
                doc_pan_showLess.visibility = View.VISIBLE
                doc_pan_showMore.visibility = View.GONE
            }
            isPancardVisible = !isPancardVisible
        }
        doc_submitPanBT.setOnClickListener {

            panName = panNameET.text.toString().trim()
            panNo = panNoET.text.toString().trim()
            loadingDialog.startLoadingDialog()

            if (validatePanNo(panNoET) && validateName(panNameET) && req_code == 4) {
                panDetails()
            } else {
                Handler(Looper.myLooper()!!).postDelayed({
                    loadingDialog.dismissDialog()
                    Toast.makeText(
                        this@DocumentStatus,
                        "please fill all details",
                        Toast.LENGTH_SHORT
                    ).show()
                }, 2000)

            }

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
        doc_bankLL.setOnClickListener {
            if (isBankVisible) {
                doc_bankCl.visibility = View.GONE
                doc_bank_showLess.visibility = View.GONE
                doc_bank_showMore.visibility = View.VISIBLE
            } else {
                doc_bankCl.visibility = View.VISIBLE
                doc_bank_showLess.visibility = View.VISIBLE
                doc_bank_showMore.visibility = View.GONE
            }
            isBankVisible = !isBankVisible
        }
        doc_submitBankBt.setOnClickListener {
            bName = bNameET.text.toString()
            bAcount = accountNOET.text.toString()
            bRAcount = raccountNOET.text.toString()
            bifsc = ifsc_code.text.toString()
            loadingDialog.startLoadingDialog()

            if (validateBankName(bNameET) && validateAccountNo(accountNOET) && validateAccountNo(
                    raccountNOET
                ) && validateIFSC(ifsc_code) && req_code == 5
            ) {
                if (accountNOET.text.toString() == raccountNOET.text.toString()) {
                    bankDetails()
                } else {
                    loadingDialog.dismissDialog()
                    Toast.makeText(
                        this@DocumentStatus,
                        "please check account number",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Handler(Looper.myLooper()!!).postDelayed({
                    loadingDialog.dismissDialog()
                    Toast.makeText(
                        this@DocumentStatus,
                        "please fill all details",
                        Toast.LENGTH_SHORT
                    ).show()
                }, 2000)

            }

        }

        okBT.setOnClickListener {
            if (isSelfie == true && isAdharcard == true && isAddress == true && isMerchant == true && isPancard == true && isBank == true) {
                Log.d("isSelfiee", isSelfie.toString())
                if (prefManager.getCode() == 1) {

                    startActivity(Intent(this@DocumentStatus, HomeScreen::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@DocumentStatus, VerificationActivity::class.java))
                    finish()
                }

            } else {

                Toast.makeText(this, "please fill the all details first", Toast.LENGTH_SHORT).show()
            }
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
        doc_submitMerchantBT.setOnClickListener {
//            getMerchant()
            loadingDialog.startLoadingDialog()
            submitMerchants()
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
        /*  pan_dob.setOnClickListener {
              req_code = 1
              showDatePicker()
          }*/


        ivback.setOnClickListener {
            onBackPressed()
        }
        // camera
//        imageUri = createImageUri()!!
        adharFrontIV.setOnClickListener {
            ImagePicker.with(this)
                .crop(16F, 9F)
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
        adhadharRear.setOnClickListener {
            ImagePicker.with(this)
                .crop(
                    16F,
                    9f
                )                         //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
            image_type = 2
        }
        panFront.setOnClickListener {
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
        passbookIV.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
            image_type = 4
        }

        doc_profile.setOnClickListener {
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
                )                    //Final image resolution will be less than 1080 x 1080(Optional)
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

        if (isAdharcard) {
            Log.d("isAdharcard", isAdharcard.toString())
            doc_aadharError.visibility = View.GONE
            doc_submitAdharBT.visibility = View.GONE
            doc_aadharOK.visibility = View.VISIBLE
        }


        getMerchant()

//        submitMerchants()

    }

    fun adharClose() {
        doc_constraintLayout.visibility = View.GONE
        doc_aadhar_showLess.visibility = View.GONE
        doc_aadhar_showMore.visibility = View.VISIBLE
    }

    fun adharOpen() {
        doc_constraintLayout.visibility = View.VISIBLE
        doc_aadhar_showLess.visibility = View.VISIBLE
        doc_aadhar_showMore.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions

            if (image_type == 1) {
                adharFrontIV.setImageURI(uri)
                adharFrontIV.maxWidth = 50

                var bitmap = MediaStore.Images.Media.getBitmap(
                    this.contentResolver,
                    uri
                )
                b64 = BitMapToString(bitmap).toString()


                Log.d("Image64QQ", "XXX " + b64)
                req_code = 2

            } else if (image_type == 2) {
                adhadharRear.setImageURI(uri)

                var bitmap = MediaStore.Images.Media.getBitmap(
                    this.contentResolver,
                    uri
                )
                b64 = BitMapToString(bitmap).toString()

                Log.d("Image64QQ", "yyy " + b64)
                req_code = 3

            } else if (image_type == 3) {
                panFront.setImageURI(uri)
                var bitmap = MediaStore.Images.Media.getBitmap(
                    this.contentResolver,
                    uri
                )
                b64 = BitMapToString(bitmap).toString()
                req_code = 4
            } else if (image_type == 4) {
                passbookIV.setImageURI(uri)
                var bitmap = MediaStore.Images.Media.getBitmap(
                    this.contentResolver,
                    uri
                )
                b64 = BitMapToString(bitmap).toString()
                req_code = 5
            } /*else if (image_type == 5) {
                licenceIV.setImageURI(uri)
            }*/ else if (image_type == 6) {

                doc_profile.setImageURI(uri)
//                val bitmap = BitmapFactory.decodeFile(file.toString())

                var bitmap = MediaStore.Images.Media.getBitmap(
                    this.contentResolver,
                    uri
                )
                b64 = BitMapToString(bitmap).toString()
//                isSelfie = true
                req_code = 1
                Log.d("Image64QQ", "XXX " + b64)
                Log.d("Image64XX", "YY " + BitMapToString(bitmap))
            } else if (image_type == 11) {
                addressProofIV.setImageURI(uri)

                val bitmap = MediaStore.Images.Media.getBitmap(
                    this.contentResolver,
                    uri
                )
                b64 = BitMapToString(bitmap).toString()
                req_code = 6

            }


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    fun selfieDetails() {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val selfieApi = retrofit.create(SelfieApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val bearerToken = prefManager.getToken()

                val selfieRequestData = SelfieRequestData(
                    selfiee = b64
//          bearerToken = prefManager.getToken()
                )
                Log.d("SentRes", "$bearerToken $b64")
                val response = selfieApi.uploadImage("Bearer $bearerToken", selfieRequestData)
                if (response.isSuccessful) {
                    val responseData = response.body()
                    Log.d("Response", responseData.toString())
                    if (responseData != null) {
                        val msg = responseData.msg
                        val status = responseData.status
//                        prefManager.setCode(1)
                        // Handle successful responser
                        runOnUiThread {
                            Handler().postDelayed({
                                // After 4 seconds
                                loadingDialog.dismissDialog()

                                doc_selfieCL.visibility = View.GONE
                                doc_selfieError.visibility = View.GONE
                                doc_selfieOK.visibility = View.VISIBLE
                                doc_selfie_showLess.visibility = View.GONE
                                doc_selfie_showMore.visibility = View.VISIBLE
                                doc_selfieUpdateBT.visibility = View.GONE
                                isSelfie = true
                            }, 4000) // 4 seconds
                        }
                        Log.d("msgSelfie", msg)
                    } else {
                        // Handle case when responseData is null

                        Log.d("else", "t.toString")
                    }
                } else {
                    // Handle unsuccessful response
                    val errorBody = response.errorBody()
                    val errorMessage = errorBody?.string()
//                    showErrorDialog(errorMessage.toString(),"ok")
                    // Handle the case when the API call is unsuccessful
                    // Log or display the error message
                    Log.e(
                        "API Error",
                        "token \n $bearerToken body: $errorBody, errorMessage: $errorMessage"
                    )
                }
            } catch (e: Exception) {
                Log.e("API Exception", "${e.message}")
            }
        }
    }

    fun adharDetails() {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // Make the API request
        val adharApi = retrofit.create(AdharApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Replace "YOUR_TOKEN" with your actual bearer token
                val token = prefManager.getToken()

                val requestData = AdharRequestData(
                    adhar_front = b64,
                    adhar_back = b64,
                    adhar_no = adharNo
                )

                val response = adharApi.saveAdharData("Bearer $token", requestData)
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        val msg = responseData.msg
                        val status = responseData.status
                        // Process the response data as needed
                        runOnUiThread {
                            Handler(Looper.myLooper()!!).postDelayed({
                                // After 4 seconds
                                loadingDialog.dismissDialog()

                                adharClose()
                                doc_aadharError.visibility = View.GONE
                                doc_aadharOK.visibility = View.VISIBLE
                                doc_submitAdharBT.visibility = View.GONE
                                adhadharF.isEnabled = false
//            adhadharF.visibility = View.GONE
                                adhadharR.isEnabled = false
                                doc_adharNoET.isEnabled = false
                                isAdharcard = true
//            adhadharR.visibility = View.GONE
                            }, 4000) // 4 seconds
                        }
                        Log.d("msg", msg + adharNo)
                    } else {
                        // Handle case when responseData is null
                        Log.d("else", "t.toString")
                    }
                } else {
                    // Handle unsuccessful response
                    val errorBody = response.errorBody()
                    val errorMessage = errorBody?.string()
//                    showErrorDialog(errorMessage.toString(),"ok")
                    // Handle the case when the API call is unsuccessful
                    // Log or display the error message
                    Log.e("API Error", "body: $errorBody, errorMessage: $errorMessage")
                }
            } catch (e: Exception) {
                // Handle network or other errors

                withContext(Dispatchers.Main) {
                    Log.e("API Error", "Exception: $e")
                    showErrorDialog("An error occurred", "OK")
                }
            }
        }

    }

    fun panDetails() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // Make the API request
        val panApi = retrofit.create(PanApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Replace "YOUR_TOKEN" with your actual bearer token
                val token = prefManager.getToken()

                val requestData = PanRequestData(
                    pan_image = b64,
                    pan_no = panNo,
                    pan_name = panName
                )

                val response = panApi.savePanData("Bearer $token", requestData)
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        val msg = responseData.msg

                        val status = responseData.status
                        // Process the response data as needed
                        runOnUiThread {
                            Handler().postDelayed({
                                // After 4 seconds
                                loadingDialog.dismissDialog()

                                doc_pancardCL.visibility = View.GONE
                                doc_pan_showLess.visibility = View.GONE
                                doc_pan_showMore.visibility = View.VISIBLE
                                doc_panError.visibility = View.GONE
                                doc_panOK.visibility = View.VISIBLE
                                doc_submitPanBT.visibility = View.GONE
                                panNoET.isEnabled = false
                                panNameET.isEnabled = false
                                addPan.isEnabled = false
                                isPancard = true
                            }, 4000) // 4 seconds
                        }
                        Log.d("msg", msg + panNo + status)
                    } else {
                        // Handle case when responseData is null
                        Log.d("else", "t.toString")
                    }
                } else {
                    // Handle unsuccessful response
                    val errorBody = response.errorBody()
                    val errorMessage = errorBody?.string()
//                    showErrorDialog(errorMessage.toString(),"ok")
                    // Handle the case when the API call is unsuccessful
                    // Log or display the error message
                    Log.e("API Error", "body: $errorBody, errorMessage: $errorMessage")
                }
            } catch (e: Exception) {
                // Handle network or other errors

                withContext(Dispatchers.Main) {
                    Log.e("API Error", "Exception: $e")
                    showErrorDialog("An error occurred", "OK")
                }

            }
        }
    }

    fun addressDetails() {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val addressApi = retrofit.create(AddressApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token = prefManager.getToken()
                val requestData = AddressRequestData(
                    city,
                    selectedItem,/*+ "+" + state_id,*/
                    landmark,
                    addrass,
                    pinCode,
                    b64
                )
                val response = addressApi.updateProfileData("Bearer $token", requestData)
                Log.d("ResponseBody", response.body().toString())
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        val message = responseData.msg
                        // Handle the success message as needed
                        runOnUiThread {
                            Handler().postDelayed({
                                // After 4 seconds
                                loadingDialog.dismissDialog()
                                doc_updateAddressBT.visibility = View.GONE
                                doc_addressLL.visibility = View.GONE
                                doc_address_showLess.visibility = View.GONE
                                doc_address_showMore.visibility = View.VISIBLE
                                doc_addressOK.visibility = View.VISIBLE
                                doc_addressError.visibility = View.GONE
                                doc_address.isEnabled = false
                                doc_city.isEnabled = false
                                doc_landmark.isEnabled = false
                                pin_code.isEnabled = false
                                doc_select_state.isEnabled = false
                                addressProofTV.isEnabled = false
                                isAddress = true
                            }, 4000) // 4 seconds
                        }
                        Log.d("msg", message)
                    } else {
                        // Handle the case when responseData is null or status is not "201"
                        Log.d("else", "t.toString")
                    }
                } else {
                    // Handle the case when the API call is unsuccessful
                    val errorBody = response.errorBody()?.string()
                    // Log or handle the error response as needed
                    Log.d("Api Error", "$errorBody")
                }
            } catch (e: Exception) {
                // Handle the network or other errors
                Log.e(
                    "API ErrorC",
                    "Error: ${e.message} ${e.localizedMessage} ${e.cause} ${e.stackTrace}"
                )
            }
        }

    }

    fun bankDetails() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val bankApi = retrofit.create(BankApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token = prefManager.getToken()
                val requestData = BankRequestData(
                    bName,
                    bAcount,
                    bifsc,
                    b64
                )
                val response = bankApi.updateBankData("Bearer $token", requestData)
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        val msg = responseData.msg
                        Log.d("msg", msg)
                        runOnUiThread {
                            Handler().postDelayed({
                                // After 4 seconds
                                loadingDialog.dismissDialog()
                                doc_bankCl.visibility = View.GONE
                                doc_bank_showLess.visibility = View.GONE
                                doc_submitBankBt.visibility = View.GONE
                                doc_bankError.visibility = View.GONE
                                doc_bank_showMore.visibility = View.VISIBLE
                                doc_bankOk.visibility = View.VISIBLE
                                bNameET.isEnabled = false
                                accountNOET.isEnabled = false
                                raccountNOET.isEnabled = false
                                ifsc_code.isEnabled = false
                                addPassbook.isEnabled = false
                                isBank = true
                            }, 2000) // 4 seconds
                        }
                    } else {
                        Log.d("else", "t.toString")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    // Log or handle the error response as needed
                    Log.d("Api Error", "$errorBody")
                }
            } catch (e: Exception) {

            }
        }

    }

    fun getMerchant() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val merchantApi = retrofit.create(MerchantApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token = prefManager.getToken() // Replace with your actual bearer token

                val response = merchantApi.getMerchants("Bearer $token")
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {

                        val merchants = responseData.merchants
                        // Switch to the main thread to update UI
//                        withContext(`ispatchers.Main)

                        runOnUiThread {

                            for (merchant in merchants) {
                                val checkBoxId = merchant.id
                                val checkBoxName = merchant.name

                                listCard.add(
                                    Merchant(
                                        checkBoxId, checkBoxName
                                    )
                                )
                                Log.d("CheckBox1", "Merchant ID: $checkBoxId")
                                checkBox.text = checkBoxName
                                /*   checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                                       if (isChecked) {
                                           selectedMerchantIds.add(checkBoxId)
                                           Log.d("CheckBox2", "Merchant $checkBoxId selected")
                                       } else {
                                           selectedMerchantIds.remove(checkBoxId)
                                           Log.d("CheckBox3", "Merchant $checkBoxId deselected")
                                       }
                                   }*/

                            }

                            Log.d("checkBox", "$merchants")
                        }
                    } else {
                        // Handle case when responseData is null
                        Log.d("else", "Response data is null")
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e("API Error", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                // Handle network or other errors
                Log.e("Error", "Exception: ${e.message}")
            }
        }

    }

    fun submitMerchants() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val merchantApi = retrofit.create(MerchantApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token = prefManager.getToken()

                // Retrieve the selected merchant IDs from the adapter
                val selectedMerchantIds = adapterCheckBox.getSelectedMerchants()
                val selectedMerchants =
                    mutableListOf(selectedMerchantIds) // Replace with the IDs of the selected merchants

                Log.d("selectedMerchants", selectedMerchants.toString())

                val requestBody = SubmitMerchantsRequest(selectedMerchants)

                val response = merchantApi.submitMerchants("Bearer $token", requestBody)
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        val merchants = requestBody.merchants
                        val status = responseData.status
                        val message = responseData.msg

                        Log.d(
                            "msgSUbmit",
                            "$message $merchants ${selectedMerchants.size}  $selectedMerchants $status ${selectedMerchantIds.size} "
                        )
                        Log.d("ResponseM", "$merchants ")
                        // Handle the response accordingly
                        runOnUiThread {

                                // After 4 seconds
                                loadingDialog.dismissDialog()
                                if (selectedMerchantIds.isNotEmpty()) {

                                    doc_merchantError.visibility = View.GONE
                                    doc_merchantLL.visibility = View.GONE
                                    doc_merchant_showLess.visibility = View.GONE
                                    doc_merchant_showMore.visibility = View.VISIBLE
                                    doc_merchantOK.visibility = View.VISIBLE
                                    doc_submitMerchantBT.visibility = View.GONE
                                    isMerchant = true

                                } else {
                                    Toast.makeText(
                                        this@DocumentStatus,
                                        "please select merchants",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }


                        }
                    } else {
                        // Handle case when responseData is null
                        Log.d("else", "Response data is null")
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e("API Error", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                // Handle network or other errors
                Log.e("Error", "Exception: ${e.message}")
            }
        }
    }

    fun getDocStatus() {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
//            .client(createOkHttpClient())
            .build()


        val profileService = retrofit.create(ProfileApi::class.java)


//                val response = profileApi.getProfileData()

        val token = prefManager.getToken()
        val call = profileService.getProfileData("Bearer $token")
        call.enqueue(object : Callback<ProfileData> {
            override fun onResponse(call: Call<ProfileData>, response: Response<ProfileData>) {
                if (response.isSuccessful) {
                    val profileData = response.body()
                    if (profileData != null) {
                        val profile = profileData.profileData
                        val message = profileData.message
                        val merchants = profileData.merchants
                        val profilePicUrl = profile?.profilePic
                        val mobile = profile?.mobile
                        val name = profile?.name
                        val dob = profile?.dob
                        val address = profile?.address
                        val state = profile?.state
                        val city = profile?.city
                        val landmaek = profile?.landmark
                        val pincode = profile?.pincode
                        val adharNo = profile?.adharNo
                        val adharFrontPicUrl = profile?.adharFrontPic
                        val adharBackPicUrl = profile?.adharBackPic
                        val addressProofPicUrl = profile?.addressProofPic
                        val otherDetails = profileData.otherDetails
                        val bankName = otherDetails?.bankName
                        val accountNo = otherDetails?.accountNo
                        val ifscCode = otherDetails?.ifscCode
                        val panNo = otherDetails?.panNo
                        val panName = otherDetails?.panName
                        val panPhotoUrl = otherDetails?.panPhoto
                        val bankPhotoUrl = otherDetails?.bankPhoto



                        doc_adharNoET.setText(adharNo.toString())
                        doc_address.setText(address.toString())
                        doc_city.setText(city)
                        doc_landmark.setText(landmaek)
                        pin_code.setText(pincode)
                        val stateIndex = items.indexOf(state)
                        doc_select_state.setSelection(stateIndex)
                        panNoET.setText(panNo)
                        panNameET.setText(panName)

                        ifsc_code.setText(ifscCode)
                        bNameET.setText(bankName)
                        accountNOET.setText(accountNo)
                        raccountNOET.setText(accountNo)

                        Picasso.get().load(profilePicUrl).placeholder(R.drawable.profile)
                            .into(doc_profile)
                        Picasso.get().load(adharFrontPicUrl).placeholder(R.drawable.dummy_aadhar)
                            .into(adharFrontIV)
                        Picasso.get().load(adharBackPicUrl).placeholder(R.drawable.dummy_aadhar)
                            .into(adhadharRear)
                        Picasso.get().load(addressProofPicUrl).placeholder(R.drawable.dummy_dl)
                            .into(addressProofIV)
                        Picasso.get().load(panPhotoUrl).placeholder(R.drawable.dummy_pancard)
                            .into(panFront)
                        Picasso.get().load(bankPhotoUrl).placeholder(R.drawable.passbook)
                            .into(passbookIV)

                        if (state != null && city != null && landmaek != null && address != null && pincode != null) {
                            isAddress = true
                            doc_addressError.visibility = View.GONE
                            doc_updateAddressBT.visibility = View.GONE
                            doc_addressOK.visibility = View.VISIBLE
                            doc_address.isEnabled = false
                            doc_city.isEnabled = false
                            doc_landmark.isEnabled = false
                            pin_code.isEnabled = false
                            doc_select_state.isEnabled = false
                            addressProofTV.isEnabled = false
                        } else {
                            doc_address.setText("")
                            doc_city.setText("")
                            pin_code.setText("")
                            doc_landmark.setText("")
                        }
                        if (bankName != null && accountNo != null && ifscCode != null) {
                            isBank = true
                            doc_bankError.visibility = View.GONE
                            doc_submitBankBt.visibility = View.GONE
                            doc_bankOk.visibility = View.VISIBLE
                            bNameET.isEnabled = false
                            accountNOET.isEnabled = false
                            raccountNOET.isEnabled = false
                            ifsc_code.isEnabled = false
                            addPassbook.isEnabled = false
                        }
                        if (adharNo != null && adharFrontPicUrl != null && adharBackPicUrl != null) {
                            isAdharcard = true
                            doc_aadharError.visibility = View.GONE
                            doc_submitAdharBT.visibility = View.GONE
                            doc_aadharOK.visibility = View.VISIBLE
                            doc_adharNoET.isEnabled = false
                            adhadharF.isEnabled = false
//                            adhadharF.visibility = View.GONE
                            adhadharR.isEnabled = false
//                            adhadharR.visibility = View.GONE
                            Log.d("isAdharcard", isAdharcard.toString())
                        } else {
                            doc_adharNoET.setText("")
                            doc_adharNoET.isEnabled = true
                        }
                        if (profile != null) {
                            isSelfie = true
                            doc_selfieError.visibility = View.GONE
                            doc_selfieUpdateBT.visibility = View.GONE
                            doc_add_selfie.visibility = View.GONE
                            doc_selfieOK.visibility = View.VISIBLE
                        }
                        if (panName != null || panNo != null || panPhotoUrl != null) {
                            isPancard = true
                            doc_panError.visibility = View.GONE
                            doc_submitPanBT.visibility = View.GONE
                            doc_panOK.visibility = View.VISIBLE
                            panNoET.isEnabled = false
                            panNameET.isEnabled = false
                            addPan.isEnabled = false
                        }

                        /*   if (merchant != null) {

   //                            checkBox.text = merchant
                                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                                  if (isChecked) {
                                         selectedMerchantIds.add(merchant)
                                         Log.d("CheckBox2", "Merchant $checkBoxId selected")
                                     }
                                 }
   //                            adapterCheckBox
                           }*/

                        Log.d("profile", "" + profilePicUrl)
                        Log.d("profile", "$profile $otherDetails $merchants")
                        Log.d("merchantsQQ", "$merchants")
                        //prefManager.setList(merchant as List<String>)
                        Log.d("msg", "$message")
//                        if (merchants != null) {
                        if (merchants?.isNotEmpty() == true) {
                            merchantWorkingLL.visibility = View.GONE
                            checkboxLL.visibility = View.VISIBLE
                            for (i in 0..merchants.size - 1) {
                                if (i != merchants.size - 1)
                                    list += merchants[i].id.toString() + ","
                                else
                                    list += merchants[i].id.toString()
                            }
//                                merchants.toImmutableList()
                            doc_submitMerchantBT.visibility = View.GONE
                            doc_merchantError.visibility = View.GONE
                            doc_merchantOK.visibility = View.VISIBLE
                            checkboxLL.isClickable = false
                            isMerchant = true
                        } else {
                            /*  merchantWorkingLL.visibility = View.VISIBLE
                              checkboxLL.visibility = View.GONE
                              doc_submitMerchantBT.visibility = View.VISIBLE
                              doc_merchantError.visibility = View.VISIBLE
                              doc_merchantOK.visibility = View.GONE*/
                        }
//                        }
                        prefManager.setIds(list)

                        checkBoxRV.layoutManager = GridLayoutManager(this@DocumentStatus, 2)
                        checkBoxRV.adapter = adapterCheckBox


                    } else {
                        Log.d("ElseSignup ", "t.toString()")
                        val errorResponseCode = response.code()
                        val errorResponseBody = response.errorBody()?.string()
                        // Handle the error response code and body
                        Log.e(
                            "API Error",
                            "Response Code: $errorResponseCode, Body: $errorResponseBody"
                        )
                        showToast("documents updated")
                    }
                }
            }

            override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                when (t) {
                    is SocketTimeoutException -> {
                        // Handle SocketTimeoutException
                        Log.d("fail1", "Socket Timeout: ${t.message}")
                        val errorMessage = "Socket Timeout: ${t.message}"
                        showErrorToast(errorMessage)
                    }
                    is IOException -> {
                        // Handle IOException
                        Log.d("fail", "IO Exception: ${t.message}")
                        val errorMessage = "IO Exception: ${t.message}"
                        showErrorToast(errorMessage)
                    }
                    else -> {
                        // Handle other types of exceptions or generic error
                        val errorMessage = "Error: ${t.message}"
                        Log.d("fail3", "Error: ${t.message}")
                        showErrorToast(errorMessage)
                    }
                }
            }

        })

    }

    // Utility function to show Toast messages
}