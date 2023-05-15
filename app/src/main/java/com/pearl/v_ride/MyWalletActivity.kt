package com.pearl.v_ride


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pearl.adapter.TransactionsAdapter
import com.pearl.data.TransactionList
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
    lateinit var rootView: LinearLayout
    lateinit var makePaymentBtn: Button
    var i: Int = 0

    private lateinit var resourcess : Resources
    lateinit var titleTV: TextView
    lateinit var totalBalanceTV: TextView
    lateinit var securityAmtTV: TextView
    lateinit var withdrawable_balanceTV: TextView
    lateinit var payDueTV: TextView
    lateinit var rechargeTV: TextView
    lateinit var withdrawTV: TextView
    lateinit var transactionTV: TextView

    // pay due
    lateinit var payDue: TextView
    lateinit var status: TextView
    lateinit var unpaid: TextView
    lateinit var due_date: TextView
    lateinit var user_name: TextView
    lateinit var nameTV: TextView
    lateinit var user_id: TextView
    lateinit var bill_amount: TextView
    lateinit var user_idTV: TextView
    lateinit var bill_amountTV: TextView
    lateinit var button_payDue: Button

    // Withdraw
    lateinit var withdraw: TextView
    lateinit var statusWTV: TextView
    lateinit var available_balance: TextView
    lateinit var usr_name: TextView
    lateinit var amount: TextView
    lateinit var bank_name: TextView
    lateinit var bank_account_number: TextView
    lateinit var ifsc_code: TextView
    lateinit var usr_nameTV: TextView
    lateinit var amountTV: TextView
    lateinit var bank_nameTV: TextView
    lateinit var bank_account_numberTV: TextView
    lateinit var ifsc_codeTV: TextView
    lateinit var button_withdraw: Button

//    lateinit var earningProfile: ImageView
   val transactionListCard = ArrayList<TransactionList>()

    override fun setLayoutXml() {
        setContentView(R.layout.activity_my_wallet)
    }

    override fun initializeViews() {



        titleTV = findViewById(R.id.titleTV)
        totalBalanceTV = findViewById(R.id.totalBalanceTV)
        securityAmtTV = findViewById(R.id.securityAmtTV)
        withdrawable_balanceTV = findViewById(R.id.withdrawable_balanceTV)
        payDueTV = findViewById(R.id.payDueTV)
        rechargeTV = findViewById(R.id.rechargeTV)
        withdrawTV = findViewById(R.id.withdrawTV)
        transactionTV = findViewById(R.id.transaction)

        //pay due
        status = findViewById(R.id.status)
        payDue = findViewById(R.id.payDue)
        unpaid = findViewById(R.id.unpaid)
        due_date = findViewById(R.id.due_date)
        user_name = findViewById(R.id.user_name)
        nameTV = findViewById(R.id.nameTV)
        user_id = findViewById(R.id.user_id)
        bill_amount = findViewById(R.id.bill_amount)
        button_payDue = findViewById(R.id.button_payDue)

        //Withdraw
        withdraw = findViewById(R.id.withdraw)
        statusWTV = findViewById(R.id.statusWTV)
        available_balance = findViewById(R.id.available_balance)
        usr_name = findViewById(R.id.usr_name)
        amount = findViewById(R.id.amount)
        bank_name = findViewById(R.id.bank_name)
        bank_account_number = findViewById(R.id.bank_account_number)
        ifsc_code = findViewById(R.id.ifsc_code)
        usr_nameTV = findViewById(R.id.usr_nameTV)
        amountTV = findViewById(R.id.amountTV)
        bank_nameTV = findViewById(R.id.bank_nameTV)
        bank_account_numberTV = findViewById(R.id.bank_account_numberTV)
        ifsc_codeTV = findViewById(R.id.ifsc_codeTV)
        button_withdraw = findViewById(R.id.button_withdraw)

        resourcess = Global.language(this,resources)
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
        rootView = findViewById(R.id.rootView)
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
    private fun closeKeyboard() {
       // onBackPressed()
//        val view: View? = currentFocus
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(w_nameET.windowToken, 0)

    }


    @SuppressLint("ClickableViewAccessibility")
    override fun initializeClickListners() {
        ivback.setOnClickListener {
            onBackPressed()
        }

        /*rootView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Hide the keyboard
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(w_amountET.windowToken, 0)
                // Clear the focus from the EditText
                w_nameET.clearFocus()
            }
            false
        }*/

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

            val name = w_nameET.text.toString()
            w_nameTV.text = name
            val amount = w_amountET.text.toString()
            w_amountTV.text = amount
            val bank_name = w_bankNameET.text.toString()
            w_bankNameTV.text = bank_name
            val bankAC = w_bankACNOET.text.toString()
            w_bankACNOTV.text = bankAC
            val  ifsc = w_ifscCodeET.text.toString()
            w_ifscCodeTV.text = ifsc

            closeKeyboard()


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
//            dialog.setContentView(R.layout.upi_layout)
            dialog.setContentView(R.layout.upi_layout)
            amountEdt = dialog.findViewById<EditText>(R.id.idEdtAmount)
            upiEdt = dialog.findViewById<EditText>(R.id.idEdtUpi)
            nameEdt = dialog.findViewById<EditText>(R.id.idEdtName)
            descEdt = dialog.findViewById<EditText>(R.id.idEdtDescription)
            makePaymentBtn = dialog.findViewById(R.id.idBtnMakePayment)!!

            transactionDetailsTV = dialog.findViewById<TextView>(R.id.idTVTransactionDetails)
            dialog.show()


            makePaymentBtn?.setOnClickListener {

                val amt = amountEdt?.text.toString()
                val amount = Math.round(amt.toFloat() * 100)
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
                    Toast.makeText(applicationContext, "Error in payment: " + e.message, Toast.LENGTH_SHORT).show()
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
//        closeKeyboard()
        
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
            TransactionList("Transaction Title", "₹ 8500","24/04/2024","11.43PM")
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
        withdrawTV.text = resourcess.getString(R.string.withdraw)
        rechargeTV.text = resourcess.getString(R.string.recharge)
        payDueTV.text = resourcess.getString(R.string.pay_due)
        transactionTV.text = resourcess.getString(R.string.transaction)
        seeTransaction.text = resourcess.getString(R.string.see_all)
        hideTransaction.text = resourcess.getString(R.string.hide_all)
        titleTV.text = resourcess.getString(R.string.my_wallet)
        totalBalanceTV.text = resourcess.getString(R.string.total_balance)
        securityAmtTV.text = resourcess.getString(R.string.security_amount)
        withdrawable_balanceTV.text = resourcess.getString(R.string.withdrawable_balance)

        payDue.text = resourcess.getString(R.string.pay_due)
        unpaid.text = resourcess.getString(R.string.unpaid)
        status.text = resourcess.getString(R.string.status)
        due_date.text = resourcess.getString(R.string.due_date)
        user_name.text = resourcess.getString(R.string.user_name)
        bill_amount.text = resourcess.getString(R.string.bill_amount)
        button_payDue.text = resourcess.getString(R.string.pay_due)
        user_id.text = resourcess.getString(R.string.user_id)


        withdraw.text = resourcess.getString(R.string.withdraw)
        statusWTV.text = resourcess.getString(R.string.status)
        available_balance.text = resourcess.getString(R.string.available_balance)
        usr_name.text = resourcess.getString(R.string.user_name)
        amount.text = resourcess.getString(R.string.amount)
        bank_name.text = resourcess.getString(R.string.bank_name)
        bank_account_number.text = resourcess.getString(R.string.bank_account_number)
        ifsc_code.text = resourcess.getString(R.string.ifsc_code)
        showMoreTV.text = resourcess.getString(R.string.show_more)
        showLessTV.text = resourcess.getString(R.string.show_less)
        usr_nameTV.text = resourcess.getString(R.string.user_name)
        amountTV.text = resourcess.getString(R.string.amount)
        bank_nameTV.text = resourcess.getString(R.string.bank_name)
        bank_account_numberTV.text = resourcess.getString(R.string.bank_account_number)
        ifsc_codeTV.text = resourcess.getString(R.string.ifsc_code)
        button_withdraw.text = resourcess.getString(R.string.withdraw)
     /*   makePaymentBtn.text = resourcess.getString(R.string.make_payment)
        amountEdt!!.hint = resourcess.getString(R.string.enter_amount_to_be_paid)
        upiEdt!!.hint = resourcess.getString(R.string.enter_your_upi_id)
        nameEdt!!.hint = resourcess.getString(R.string.enter_your_name)
        descEdt!!.hint = resourcess.getString(R.string.enter_payment_description)*/

    }

    override fun onPaymentSuccess(s: String?) {

        Toast.makeText(this, "Payment is successful : $s", Toast.LENGTH_SHORT).show()

    }

    override fun onPaymentError(i: Int, s: String?) {
        Toast.makeText(this, "Payment failed$i", Toast.LENGTH_SHORT).show()

    }


}

