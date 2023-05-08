package com.pearl.v_ride

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pearl.test5.R
import com.pearl.v_ride_lib.Global
import com.pearl.v_ride_lib.SessionManager

class LanguageActivity :AppCompatActivity() {
    lateinit var langEngBtn: Button
    lateinit var langHiBtn: Button
    lateinit var context : Context
    lateinit var resourcess: Resources
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_language)
      resourcess =  Global.language(this,resources)
        langEngBtn = findViewById(R.id.btnEngLang)
        langHiBtn = findViewById(R.id.btnHindiLang)
        langEngBtn.setText(resourcess.getString(R.string.lang_eng))
        langHiBtn.setText(resourcess.getString(R.string.lang_hindi))

        langEngBtn.setOnClickListener {
            context = SessionManager.setLocale(this@LanguageActivity,"en")
            resourcess = context.resources

            finish()
            startActivity(intent)
        }
        langHiBtn.setOnClickListener {
            context = SessionManager.setLocale(this@LanguageActivity,"hi")
            resourcess = context.resources

            finish()
            startActivity(intent)
        }






        }

}