package com.pearl.v_ride

import android.app.Application
import android.content.Context
import android.view.textclassifier.TextClassificationContext
import com.pearl.v_ride_lib.Global
import com.pearl.v_ride_lib.SessionManager

class VRun : Application() {

    @Override
    override fun attachBaseContext(base:Context) {

        super.attachBaseContext(SessionManager.onChanged(base,"en"))

        
    }
}