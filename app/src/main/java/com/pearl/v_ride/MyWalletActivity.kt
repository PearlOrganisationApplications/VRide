package com.pearl.v_ride


import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pearl.adapter.TransactionsAdapter

import com.pearl.data.TransactionList
import com.pearl.v_ride_lib.Global
import com.pearl.test5.R
import com.pearl.v_ride_lib.BaseClass

class
    MyWalletActivity : BaseClass() {

     lateinit var mywalletLL: LinearLayout

     lateinit var myearningLL: LinearLayout

    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var walletProfile: ImageView
    lateinit var transaction: RecyclerView
    lateinit var seeTransaction: TextView

    lateinit var hideTransaction: TextView
    var i: Int = 0

//    lateinit var earningProfile: ImageView
   val transactionListCard = ArrayList<TransactionList>()

    override fun setLayoutXml() {
        setContentView(R.layout.activity_my_wallet)
    }

    override fun initializeViews() {
        ivback=findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)
        myearningLL = findViewById(R.id.earningLL)
        mywalletLL = findViewById(R.id.walletLL)
        walletProfile = findViewById(R.id.walletProfile)
        seeTransaction = findViewById(R.id.seeAllTV)
        transaction = findViewById(R.id.transactionRV)
        hideTransaction = findViewById(R.id.hideAllTV)

        i = intent.getIntExtra("key", 0)

        if (i == 0) {
            apptitle.text = "My Earning"
            myearningLL.visibility = View.VISIBLE
            mywalletLL.visibility = View.GONE
        } else if(i == 1) {
            apptitle.text = "Wallet"
            mywalletLL.visibility = View.VISIBLE
            myearningLL.visibility = View.GONE
        }
    }

    override fun initializeClickListners() {
        ivback.setOnClickListener {
            onBackPressed()
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

        transaction.layoutManager = LinearLayoutManager(this)
        val transactionRVAdapter = TransactionsAdapter(transactionListCard)
        transaction.adapter = transactionRVAdapter
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

