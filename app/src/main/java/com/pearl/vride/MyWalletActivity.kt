package com.pearl.vride


import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.pearl.Global
import com.pearl.test5.R
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject

class MyWalletActivity : AppCompatActivity(), PaymentResultListener {

     lateinit var mywalletLL: LinearLayout

     lateinit var myearningLL: LinearLayout

    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var walletProfile: ImageView
    lateinit var recharge: LinearLayout
//    lateinit var earningProfile: ImageView


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


    }

    override fun onPaymentSuccess(s: String?) {

        Toast.makeText(this, "payment successful", Toast.LENGTH_SHORT).show()

        try {
            Toast.makeText(this, "Payment Succesful$", Toast.LENGTH_SHORT).show()



        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Exception in onPaymentSuccess", e)
        }

    }

    override fun onPaymentError(i: Int, s: String?) {
        Toast.makeText(this, "Payment failed$i", Toast.LENGTH_SHORT).show()

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

