package com.pearl.splash_screen

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationRequest
import com.pearl.test5.R
import com.pearl.v_ride.HomeScreen
import com.pearl.v_ride.MainActivity
import com.pearl.v_ride.PermissionActivity

import com.pearl.v_ride_lib.PrefManager

import com.pearl.v_ride.WelcomeScreen
import com.pearl.v_ride_lib.Global

class SplashScreenActivity : AppCompatActivity() {

    //    private val REQUEST_CODE = 101
/*    private val REQUEST_CHECK_SETTINGS: Int=101
    private lateinit var locationRequest: LocationRequest
    var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=101

    var prefManager: PrefManager? = null

    var goneToSettings :Boolean = false*/
    lateinit var onBoardingScreen: SharedPreferences
    lateinit var backgroundImage: ImageView
    lateinit var zoomAnimation: Animation

//    lateinit var prefManager: PrefManager

    private val REQUEST_CHECK_SETTINGS: Int = 101
    private lateinit var locationRequest: LocationRequest
    var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 101
    private var REQUEST_ID_MULTIPLE_PERMISSIONS = 1

    var goneToSettings: Boolean = false
    private var prefManager: PrefManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Global.language(this,resources)
        setContentView(R.layout.activity_splash_screen)
        prefManager = PrefManager(this)

        /*       prefManager = PrefManager(this@SplashScreenActivity)
        // prefManager!!.setToken("949|vBiS1sR6b5AICFuOTyP7zrkHoNhqzEsz7wu4AsKA")
        if (prefManager!!.getWelcomeSkip().equals("yes")) {
            launchHomeScreen()
            finish()
        }*/

            backgroundImage = findViewById(R.id.iv_node)
            zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.anim)

            backgroundImage.startAnimation(zoomAnimation)
            Handler(Looper.getMainLooper()).postDelayed({

                onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE)
                var isFirstTime = onBoardingScreen.getBoolean("firstTime", true)

                if(prefManager!!.getPr() != false) {

                    if (prefManager!!.getLogin()) {
                        startActivity(Intent(this, HomeScreen::class.java))
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                    }

                    finish()
                }
               else if(isFirstTime) {

                    val editor = onBoardingScreen.edit()
                    editor.putBoolean("firstTime", false)
                    editor.commit()

                    val i = Intent(this@SplashScreenActivity, WelcomeScreen::class.java)
                    startActivity(i)
                    finish()

                }else{
                    startActivity(Intent(this@SplashScreenActivity, PermissionActivity::class.java))
                    finish()
                }




            }, 2000)



/*        Handler(Looper.getMainLooper()).postDelayed({

            if (prefManager!!.getPr()){
                if (prefManager!!.getLogin().equals("yes")) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, WelcomeScreen::class.java))

                }

            }else {
                val i = Intent(this@SplashScreenActivity, PermissionActivity::class.java)
                startActivity(i)
                finish()
            }

        },5000)



            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )*/

    }

}