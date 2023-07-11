package com.pearl.v_ride_lib

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pearl.v_ride.R

class Dialog(private val activity: Activity) {
    private var dialog: AlertDialog? = null
    private var sucessDialog: AlertDialog? = null

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
    fun showErrorBottomSheetDialog(errorMessage: String) {
        val bottomSheetDialog = BottomSheetDialog(activity)
        val view = activity.layoutInflater.inflate(R.layout.error_dialog, null)

        val errorTextView = view.findViewById<TextView>(R.id.error_loginerrorTV)
        errorTextView.text = errorMessage

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }


    fun startSucessDialog(msg:String, context: Context, activityName:Class<*>){
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.dialog_success, null))
        builder.setCancelable(true)
        sucessDialog = builder.create()
        sucessDialog?.show()

        val successMsg = sucessDialog?.findViewById<TextView>(R.id.successMsg)
        successMsg?.text = msg

        sucessDialog?.findViewById<Button>(R.id.sucess_dialog_back)?.setOnClickListener {
            var intent = Intent(context, activityName)
            context.startActivity(intent)
            sucessDialog?.dismiss()
        }
    }

    fun dismissDialog() {
        dialog?.dismiss()
    }
}