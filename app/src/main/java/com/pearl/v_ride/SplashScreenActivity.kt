package com.pearl.v_ride

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.pearl.test5.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val i = Intent(this@SplashScreenActivity, WelcomeScreen::class.java)
            startActivity(i)
            finish()
        },2000)
    }
}