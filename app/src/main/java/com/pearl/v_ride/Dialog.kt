package com.pearl.v_ride

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater

class Dialog(private val activity: Activity) {
    private var dialog: AlertDialog? = null

    @SuppressLint("InflateParams")
    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.waiting, null))
        builder.setCancelable(true)

        dialog = builder.create()
        dialog?.show()
    }

    fun dismissDialog() {

        dialog?.dismiss()

    }
}