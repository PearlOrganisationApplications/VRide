package com.pearl.v_ride_lib

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import com.pearl.v_ride.R

class Dialog(private val activity: Activity) {
    private var dialog: AlertDialog? = null

    @SuppressLint("InflateParams")
    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.waiting, null))
        builder.setCancelable(false)

        dialog = builder.create()
        try {
            dialog?.show()
        } catch (e: Exception) {
            Log.d("catchBlock ",e.localizedMessage)
        }

    }

    fun dismissDialog() {

        dialog?.dismiss()

    }
}