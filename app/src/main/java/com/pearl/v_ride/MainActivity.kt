package com.pearl.v_ride

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.pearl.test5.R

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signup = findViewById<TextView>(R.id.signupBt)
        val login = findViewById<Button>(R.id.loginBT)

        signup.setOnClickListener {
            startActivity(Intent(this@MainActivity,SignUpActivity::class.java))
//            finish()
        }

        login.setOnClickListener {
            startActivity(Intent(this@MainActivity,HomeScreen::class.java))
        }
    }
}