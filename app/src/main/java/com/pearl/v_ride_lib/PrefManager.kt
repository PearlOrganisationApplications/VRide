package com.pearl.v_ride_lib

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
     fun setPermissionDeniedCount(cut:Int){
         var x = this.getPermissionDeniedCount()
         if(cut==0)
             x=0
         else
             x+=1
         editor?.putInt("deniedCount",x)
         editor?.commit()
     }
     fun getPermissionDeniedCount():Int{
         return pref?.getInt("deniedCount",0)!!
     }
     fun setLogin(Login: String) {
         editor?.putString("set",Login)
         editor?.commit()

     }

     fun getLogin(): String {
         return pref?.getString("Login","null").toString()
     }


 }