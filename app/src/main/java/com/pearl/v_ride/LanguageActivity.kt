package com.pearl.v_ride

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.pearl.test5.R
import com.pearl.v_ride_lib.Global
import com.pearl.v_ride_lib.SessionManager

class LanguageActivity :AppCompatActivity() {
    lateinit var langEngBtn: Button
    lateinit var langHiBtn: Button
    lateinit var context : Context
    lateinit var resourcess: Resources
    lateinit var selectLanguage: TextView
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_language)
      resourcess =  Global.language(this,resources)
        langEngBtn = findViewById(R.id.btnEngLang)
        langHiBtn = findViewById(R.id.btnHindiLang)
        selectLanguage =findViewById(R.id.selectLanguage)
        langEngBtn.setText(resourcess.getString(R.string.lang_eng))
        langHiBtn.setText(resourcess.getString(R.string.lang_hindi))
        ivback = findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)
        apptitle.setText(R.string.document)

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

    override fun onResume() {
        super.onResume()
        selectLanguage.text = resourcess.getString(R.string.select_language)
        apptitle.text = resourcess.getString(R.string.document)
    }

}