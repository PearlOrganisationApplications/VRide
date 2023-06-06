package com.pearl.v_ride

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.pearl.v_ride_lib.PrefManager

class VerificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_verification)
        supportActionBar?.height
        var number = findViewById<LinearLayout>(R.id.call_us)
        var email = findViewById<LinearLayout>(R.id.email_us)
        var exit = findViewById<Button>(R.id.exit)
        var intent_call = Intent(Intent.ACTION_DIAL)
        var pref = PrefManager(this)
//          var name:String = pref.getDriverName()
        var tv2 = findViewById<TextView>(R.id.textView2)
//          val args = arguments
        val waiting_layout = findViewById<CardView>(R.id.waiting_cardview)
//        prefManager.setRegistrationToken("Done")
          waiting_layout.visibility= View.VISIBLE
    }

}