package com.pearl.v_ride


import android.annotation.SuppressLint
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pearl.adapter.TransactionsAdapter
import com.pearl.data.TransactionList
import com.pearl.test5.R
import com.pearl.v_ride_lib.BaseClass
import com.pearl.v_ride_lib.Global
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class
    MyWalletActivity : BaseClass(), PaymentResultListener {

     lateinit var mywalletLL: LinearLayout

     lateinit var myearningLL: LinearLayout

    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var walletProfile: ImageView
    lateinit var transaction: RecyclerView
    lateinit var seeTransaction: TextView

    lateinit var  dialog : BottomSheetDialog
    lateinit var  dialogW : Dialog
    lateinit var recharge: LinearLayout
    private var amountEdt: EditText? = null
    private  var upiEdt:EditText? = null
    private  var nameEdt: EditText? = null
    private  var descEdt:EditText? = null
    private var transactionDetailsTV: TextView? = null
//    private val UPI_PAYMENT = 0
    lateinit var dueLayout: ScrollView
    lateinit var withdrawLayout: ScrollView
    lateinit var duePayBT: LinearLayout
    lateinit var withdrawBT: LinearLayout
    lateinit var hideTransaction: TextView
    lateinit var cancelDue: ImageView
    lateinit var closeWithdrawLL: ImageView
    lateinit var confirmDetails: ImageView
    lateinit var editDetails: ImageView
    lateinit var showWithdrawDetails: LinearLayout
    lateinit var detailFormLL: LinearLayout
    lateinit var more_detailLL: LinearLayout
    lateinit var showMoreTV: TextView
    lateinit var showLessTV: TextView
    lateinit var  w_nameET: EditText
    lateinit var  w_amountET: EditText
    lateinit var  w_bankNameET: EditText
    lateinit var  w_bankACNOET: EditText
    lateinit var  w_ifscCodeET: EditText
    lateinit var w_nameTV: TextView
    lateinit var w_amountTV: TextView
    lateinit var w_bankNameTV: TextView
    lateinit var w_bankACNOTV: TextView
    lateinit var w_ifscCodeTV: TextView
    var i: Int = 0

//    lateinit var earningProfile: ImageView
   val transactionListCard = ArrayList<TransactionList>()

    override fun setLayoutXml() {
        setContentView(R.layout.activity_my_wallet)
    }

    override fun initializeViews() {
        w_nameTV = findViewById(R.id.w_nameTV)
        w_amountTV = findViewById(R.id.w_amountTV)
        w_bankNameTV = findViewById(R.id.w_bankNameTV)
        w_bankACNOTV = findViewById(R.id.w_bankACNOTV)
        w_ifscCodeTV = findViewById(R.id.w_ifscCodeTV)
        w_amountET = findViewById(R.id.w_amountET)
        w_nameET = findViewById(R.id.w_nameET)
        w_bankNameET = findViewById(R.id.w_bankNameET)
        w_bankACNOET = findViewById(R.id.w_bankACNOET)
        w_ifscCodeET = findViewById(R.id.w_ifscCodeET)
        showMoreTV = findViewById(R.id.showMoreTV)
        showLessTV = findViewById(R.id.showLessTV)
        more_detailLL = findViewById(R.id.more_detailLL)
        confirmDetails = findViewById(R.id.confirmDetails)
        editDetails = findViewById(R.id.editDetails)
        showWithdrawDetails = findViewById(R.id.showWithdrawDetails)
        detailFormLL = findViewById(R.id.detailFormLL)
        closeWithdrawLL = findViewById(R.id.closeWithdrawLL)
        withdrawLayout = findViewById(R.id.withdrawLayout)
        withdrawBT = findViewById(R.id.withdrawLL)
        duePayBT = findViewById(R.id.duePayLL)
        dueLayout = findViewById(R.id.payDueLayout)
        ivback=findViewById(R.id.ivBack)
//        apptitle = findViewById(R.id.titleTVAppbar)
        myearningLL = findViewById(R.id.earningLL)
        mywalletLL = findViewById(R.id.walletLL)
//        walletProfile = findViewById(R.id.walletProfile)
        seeTransaction = findViewById(R.id.seeAllTV)
        transaction = findViewById(R.id.transactionRV)
        hideTransaction = findViewById(R.id.hideAllTV)
        recharge = findViewById(R.id.recharge)
        cancelDue = findViewById(R.id.cancelDue)
        dialog = BottomSheetDialog(this)
        dialogW = Dialog(this)

        i = intent.getIntExtra("key", 0)

        if (i == 0) {
//            apptitle.text = "My Earning"
            myearningLL.visibility = View.VISIBLE
            mywalletLL.visibility = View.GONE
        } else if(i == 1) {
//            apptitle.text = "Wallet"
            mywalletLL.visibility = View.VISIBLE
            myearningLL.visibility = View.GONE
        }
    }

    override fun initializeClickListners() {
        ivback.setOnClickListener {
            onBackPressed()
        }
        showMoreTV.setOnClickListener {
            more_detailLL.visibility = View.VISIBLE
            showLessTV.visibility = View.VISIBLE
            showMoreTV.visibility = View.GONE
        }
        showLessTV.setOnClickListener {
            more_detailLL.visibility = View.GONE
            showLessTV.visibility = View.GONE
            showMoreTV.visibility = View.VISIBLE
        }
        confirmDetails.setOnClickListener {
            showWithdrawDetails.visibility = View.VISIBLE
            detailFormLL.visibility = View.GONE
            confirmDetails.visibility = View.GONE
            editDetails.visibility = View.VISIBLE
            showMoreTV.visibility = View.VISIBLE
            more_detailLL.visibility = View.GONE

        }
        editDetails.setOnClickListener {
            showWithdrawDetails.visibility = View.GONE
            detailFormLL.visibility = View.VISIBLE
            confirmDetails.visibility = View.VISIBLE
            editDetails.visibility = View.GONE
            showMoreTV.visibility = View.GONE
            showLessTV.visibility = View.GONE
        }
        withdrawBT.setOnClickListener {
           withdrawLayout.visibility = View.VISIBLE
            showWithdrawDetails.visibility = View.GONE
            detailFormLL.visibility = View.VISIBLE
            confirmDetails.visibility = View.VISIBLE
            editDetails.visibility = View.GONE
            showMoreTV.visibility = View.GONE
            showLessTV.visibility = View.GONE
        }

        closeWithdrawLL.setOnClickListener {
            withdrawLayout.visibility = View.GONE
        }

        seeTransaction.setOnClickListener {
            transaction.visibility = View.VISIBLE
            seeTransaction.visibility = View.GONE
            hideTransaction.visibility = View.VISIBLE
        }

        hideTransaction.setOnClickListener {
            transaction.visibility = View.GONE
            seeTransaction.visibility = View.VISIBLE
            hideTransaction.visibility = View.GONE
        }
        duePayBT.setOnClickListener {
            dueLayout.visibility = View.VISIBLE
        }
        cancelDue.setOnClickListener {
            dueLayout.visibility = View.GONE
        }

        recharge.setOnClickListener {

//            dialog.setCancelable(false)
            dialog.setContentView(R.layout.upi_layout)
            amountEdt = dialog.findViewById<EditText>(R.id.idEdtAmount)
            upiEdt = dialog.findViewById<EditText>(R.id.idEdtUpi)
            nameEdt = dialog.findViewById<EditText>(R.id.idEdtName)
            descEdt = dialog.findViewById<EditText>(R.id.idEdtDescription)
            val makePaymentBtn: Button? = dialog.findViewById(R.id.idBtnMakePayment)
            transactionDetailsTV = dialog.findViewById<TextView>(R.id.idTVTransactionDetails)
            dialog.show()


            makePaymentBtn?.setOnClickListener {

                val amt = amountEdt?.text.toString()
                val amount = Math.round(amt.toFloat() * 100).toInt()
                val checkout = Checkout()
                checkout.setKeyID("rzp_test_capDM1KlnUhj5f")
                checkout.setImage(R.drawable.logo_round)
                val obj = JSONObject()
                try {
                    obj.put("name", "V-Ride")
                    obj.put("description", "Payment")
                    obj.put("theme.color", "")
                    obj.put("send_sms_hash", true)
                    obj.put("allow_rotation", true)
                    obj.put("currency", "INR")
                    obj.put("amount", amount)
                    val preFill = JSONObject()
                    preFill.put("email", "shubhamkhanduri@pearlorganisation.com")
                    preFill.put("contact", "91" + "8979441470")
                    obj.put("prefill", preFill)
                    checkout.open(this@MyWalletActivity, obj)
                } catch (e: JSONException) {
                    Toast.makeText(getApplicationContext(), "Error in payment: " + e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

                }
        }
    }

    override fun initializeInputs() {

    }

    override fun initializeLabels() {

    }



    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setLayoutXml()
        initializeViews()
        initializeClickListners()
        initializeInputs()
        initializeLabels()




        transactionListCard.add(
            TransactionList("Transaction Title","₹ 8500","24/04/2024","11.43PM")
        )
        transactionListCard.add(
            TransactionList("Transaction Title","₹ 8500","24/04/2024","11.43PM")
        )
        transactionListCard.add(
            TransactionList("Transaction Title","₹ 8500","24/04/2024","11.43PM")
        )
        transactionListCard.add(
            TransactionList("Transaction Title","₹ 8500","24/04/2024","11.43PM")
        )
        transactionListCard.add(
            TransactionList("Transaction Title","₹ 8500","24/04/2024","11.43PM")
        )
        transactionListCard.add(
            TransactionList("Transaction Title","₹ 8500","24/04/2024","11.43PM")
        )
        transactionListCard.add(
            TransactionList("Transaction Title","₹ 8500","24/04/2024","11.43PM")
        )
        transactionListCard.add(
            TransactionList("Transaction Title","₹ 8500","24/04/2024","11.43PM")
        )

        transaction.layoutManager = LinearLayoutManager(this)
        val transactionRVAdapter = TransactionsAdapter(transactionListCard)
        transaction.adapter = transactionRVAdapter
}



    override fun onResume() {
        super.onResume()
      /*  if(Global.imageString != "") {
            val uri = Uri.parse(Global.imageString)
            walletProfile.setImageURI(uri)
            Log.d("abc", Global.imageString)
        }*/
    }

    override fun onPaymentSuccess(s: String?) {

        Toast.makeText(this, "Payment is successful : $s", Toast.LENGTH_SHORT).show()

    }

    override fun onPaymentError(i: Int, s: String?) {
        Toast.makeText(this, "Payment failed$i", Toast.LENGTH_SHORT).show()

    }


}

