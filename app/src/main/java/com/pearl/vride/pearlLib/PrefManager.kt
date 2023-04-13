package com.figgo.customer.pearlLib

import android.content.Context
import android.content.SharedPreferences




 class PrefManager(var context: Context) {
     // Shared preferences file name
     private val PREF_NAME = "welcome"
     private val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
     // shared pref mode
     var PRIVATE_MODE = 0
    var pref: SharedPreferences? = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor: SharedPreferences.Editor? = pref!!.edit()
    var _context: Context? = context
     private val IS_VALID_LOGIN = "no"




 /*   fun PrefManager(context: Context?) {
        pref = _context
        editor =
    }*/

     fun setLogin(Login: String) {
         editor?.putString("Login",Login)
         editor?.commit()

     }

     fun getLogin(): String {
         return pref?.getString("Login","null").toString()
     }
 }