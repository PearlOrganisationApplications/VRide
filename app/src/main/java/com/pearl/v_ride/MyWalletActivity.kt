package com.pearl.v_ride


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.pearl.test5.R

class MyWalletActivity : AppCompatActivity() {

     lateinit var mywalletLL: LinearLayout

     lateinit var myearningLL: LinearLayout

    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView


    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)


               myearningLL = findViewById(R.id.earningLL)
               mywalletLL = findViewById(R.id.walletLL)

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
}
}

