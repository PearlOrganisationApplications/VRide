package com.pearl.vride



import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.pearl.Global
import com.pearl.test5.R
import java.util.*


class MyWalletActivity : AppCompatActivity() {

     lateinit var mywalletLL: LinearLayout

     lateinit var myearningLL: LinearLayout

    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var walletProfile: ImageView
    lateinit var recharge: LinearLayout
//    lateinit var earningProfile: ImageView
private var amountEdt: EditText? =
    null
    private  var upiEdt:EditText? = null
    private  var nameEdt:EditText? = null
    private  var descEdt:EditText? = null
    private var transactionDetailsTV: TextView? = null
    val UPI_PAYMENT = 0
    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)


               myearningLL = findViewById(R.id.earningLL)
               mywalletLL = findViewById(R.id.walletLL)
        recharge = findViewById(R.id.recharge)
        walletProfile = findViewById(R.id.walletProfile)

      val i: Int = intent.getIntExtra("key", 0)

        if (i == 0) {

            myearningLL.visibility = View.VISIBLE
            mywalletLL.visibility = View.GONE
        } else if(i == 1) {
            mywalletLL.visibility = View.VISIBLE
            myearningLL.visibility = View.GONE
        }

        ivback=findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)

        apptitle.text = "Wallet"
        ivback.setOnClickListener {
            onBackPressed()
        }

          recharge.setOnClickListener {

          val dialog = Dialog(this@MyWalletActivity)
          dialog.setCancelable(false)
          dialog.setContentView(R.layout.upi_layout)
              amountEdt = dialog.findViewById<EditText>(R.id.idEdtAmount)
              upiEdt = dialog.findViewById<EditText>(R.id.idEdtUpi)
              nameEdt = dialog.findViewById<EditText>(R.id.idEdtName)
              descEdt = dialog.findViewById<EditText>(R.id.idEdtDescription)
              val makePaymentBtn: Button = dialog.findViewById(R.id.idBtnMakePayment)
              transactionDetailsTV = dialog.findViewById<TextView>(R.id.idTVTransactionDetails)

              // on below line we are getting date and then we are setting this date as transaction id.

              // on below line we are getting date and then we are setting this date as transaction id.

              makePaymentBtn.setOnClickListener {
                  // on below line we are getting data from our edit text.
                  val amount = amountEdt?.getText().toString()
                  val upi = upiEdt?.getText().toString()
                  val name = nameEdt?.getText().toString()
                  val desc = descEdt?.getText().toString()
                  // on below line we are validating our text field.
                  if (TextUtils.isEmpty(amount) && TextUtils.isEmpty(upi) && TextUtils.isEmpty(name) && TextUtils.isEmpty(
                          desc
                      )
                  ) {
                      Toast.makeText(
                          this@MyWalletActivity,
                          "Please enter all the details..",
                          Toast.LENGTH_SHORT
                      )
                          .show()
                  } else {
                      // if the edit text is not empty then
                      // we are calling method to make payment.
                      payUsingUpi(amount, upi, name, desc);
                  }
              }

              if (!(this@MyWalletActivity as Activity).isFinishing) {

              try {


                  dialog.show()
              }catch (e:Exception){
                  Toast.makeText(this@MyWalletActivity, "Something went wrong!", Toast.LENGTH_LONG).show()
              }
          }
          val window: Window? = dialog.getWindow()
          window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)



      }



      /*  recharge.setOnClickListener {

            val dialog = Dialog(this@MyWalletActivity)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.
            custom_layout)
            val body = dialog.findViewById(R.id.amount) as EditText
            val yesBtn = dialog.findViewById(R.id.ok) as TextView
            val cancelBtn = dialog.findViewById(R.id.cancel) as TextView
            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }
            yesBtn.setOnClickListener {
                dialog.dismiss()
                val amt = body.text.toString()
                val amount = Math.round(amt.toFloat() * 100).toInt()
                val checkout = Checkout()
                checkout.setKeyID("rzp_test_capDM1KlnUhj5f")
                checkout.setImage(R.drawable.logo_round)
                val obj = JSONObject()
                try {
                    obj.put("name", "VRide")
                    obj.put("description", "Payment")
                    obj.put("theme.color", "")
                    obj.put("send_sms_hash", true)
                    obj.put("allow_rotation", true)
                    obj.put("currency", "INR")
                    obj.put("amount", amount)
                    val preFill = JSONObject()
                    preFill.put("email", "support@peart.com")
                    preFill.put("contact", "91" + "8077413846")
                    obj.put("prefill", preFill)
                    checkout.open(this@MyWalletActivity, obj)
                } catch (e: JSONException) {
                    Toast.makeText(this@MyWalletActivity, "Error in payment: " + e.message, Toast.LENGTH_SHORT).show();
                    e.printStackTrace()
                }

            }
            if (!(this@MyWalletActivity as Activity).isFinishing) {

                try {


                    dialog.show()
                }catch (e:Exception){
                    Toast.makeText(this@MyWalletActivity, "Something went wrong!", Toast.LENGTH_LONG).show()
                }
            }
            val window: Window? = dialog.getWindow()
            window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)



        }
*/

    }

  /*  override fun onPaymentSuccess(s: String?) {

        Toast.makeText(this, "payment successful", Toast.LENGTH_SHORT).show()

        try {
            Toast.makeText(this, "Payment Succesful$", Toast.LENGTH_SHORT).show()



        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Exception in onPaymentSuccess", e)
        }

    }

    override fun onPaymentError(i: Int, s: String?) {
        Toast.makeText(this, "Payment failed$i", Toast.LENGTH_SHORT).show()

    }*/
  open fun payUsingUpi(amount: String?, upiId: String?, name: String?, note: String?) {
      val uri = Uri.parse("upi://pay").buildUpon()
          .appendQueryParameter("pa", upiId)
          .appendQueryParameter("pn", name)
          .appendQueryParameter("tn", note)
          .appendQueryParameter("am", amount)
          .appendQueryParameter("cu", "INR")
          .build()
      val upiPayIntent = Intent(Intent.ACTION_VIEW)
      upiPayIntent.data = uri

      // will always show a dialog to user to choose an app
      val chooser = Intent.createChooser(upiPayIntent, "Pay with")

      // check if intent resolves
      if (null != chooser.resolveActivity(packageManager)) {
          startActivityForResult(chooser, UPI_PAYMENT)
      } else {
          Toast.makeText(
              this,
              "No UPI app found, please install one to continue",
              Toast.LENGTH_SHORT
          ).show()
      }
  }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            UPI_PAYMENT -> {
                if (RESULT_OK == resultCode || resultCode == 11) {
                    if (data != null) {
                        val trxt = data.getStringExtra("response")
                        //Log.d("UPI", "onActivityResult: " + trxt);
                        val dataList: ArrayList<String?> = ArrayList()
                        dataList.add(trxt)
                        upiPaymentDataOperation(dataList)
                    } else {
                        //Log.d("UPI", "onActivityResult: " + "Return data is null");
                        val dataList: ArrayList<String?> = ArrayList()
                        dataList.add("nothing")
                        upiPaymentDataOperation(dataList)
                    }
                } else {
                    //Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    val dataList: ArrayList<String?> = ArrayList()
                    dataList.add("nothing")
                    upiPaymentDataOperation(dataList)
                }
            }
        }
    }

    private fun upiPaymentDataOperation(data: ArrayList<String?>) {
        if (isConnectionAvailable(this@MyWalletActivity)) {
            var str = data[0]
            //Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            var paymentCancel = ""
            if (str == null) str = "discard"
            var status = ""
            var approvalRefNo = ""
            val response = str.split("&".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            for (i in response.indices) {
                val equalStr = response[i].split("=".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                if (equalStr.size >= 2) {
                    if (equalStr[0].lowercase(Locale.getDefault()) == "Status".lowercase(Locale.getDefault())) {
                        status = equalStr[1].lowercase(Locale.getDefault())
                    } else if (equalStr[0].lowercase(Locale.getDefault()) == "ApprovalRefNo".lowercase(
                            Locale.getDefault()
                        ) || equalStr[0].lowercase(Locale.getDefault()) == "txnRef".lowercase(
                            Locale.getDefault()
                        )
                    ) {
                        approvalRefNo = equalStr[1]
                    }
                } else {
                    paymentCancel = "Payment cancelled by user."
                }
            }
            if (status == "success") {
                //Code to handle successful transaction here.
                Toast.makeText(this@MyWalletActivity, "Transaction successful.", Toast.LENGTH_SHORT).show()
                // Log.d("UPI", "responseStr: "+approvalRefNo);
                Toast.makeText(
                    this,
                    "YOUR ORDER HAS BEEN PLACED\n THANK YOU AND ORDER AGAIN",
                    Toast.LENGTH_LONG
                ).show()
            } else if ("Payment cancelled by user." == paymentCancel) {
                Toast.makeText(this@MyWalletActivity, "Payment cancelled by user.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MyWalletActivity, "Transaction failed.Please try again", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(
                this@MyWalletActivity,
                "Internet connection is not available. Please check and try again",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun isConnectionAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val netInfo = connectivityManager.activeNetworkInfo
            if (netInfo != null && netInfo.isConnected
                && netInfo.isConnectedOrConnecting
                && netInfo.isAvailable
            ) {
                return true
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        if(Global.imageString != "") {
            val uri = Uri.parse(Global.imageString)
            walletProfile.setImageURI(uri)
            Log.d("abc", Global.imageString)
        }
    }
}

