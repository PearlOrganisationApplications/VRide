package com.pearl.v_ride_lib

import android.content.Context
import android.content.SharedPreferences




 class PrefManager(var context: Context) {
     // Shared preferences file name
     private val PREF_NAME = "welcome"
     private val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
     // shared pref mode
     var PRIVATE_MODE = 0
     private val IS_LOGGED_IN = "isLoggedIn"
    var pref: SharedPreferences? = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor: SharedPreferences.Editor? = pref!!.edit()




     fun setLogin(Login: Boolean) {
         editor?.putBoolean("login",Login)
         editor?.commit()
     }
     fun getLogin(): Boolean {
         return pref!!.getBoolean("login",false)
     }

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
 }




/*     var isLoggedIn: Boolean
         get() = pref!!.getBoolean(IS_FIRST_TIME_LAUNCH, false)
         set(value) {
             val editor = pref!!.edit()
             editor.putBoolean(IS_FIRST_TIME_LAUNCH, value)
             editor.apply()
         } */


/*
{
    fun setPermissionDeniedCount(cut: Int) {
        var x = this.getPermissionDeniedCount()
        if (cut == 0)
            x = 0
        else
            x += 1
        editor?.putInt("deniedCount", x)
        editor?.commit()
    }

    fun getPermissionDeniedCount(): Int {
        return pref?.getInt("deniedCount", 0)!!
    }


    var isLoggedIn: Boolean
    get() = pref!!.getBoolean(IS_LOGGED_IN, false)
    set(value) {
        val editor = pref!!.edit()
        editor.putBoolean(IS_LOGGED_IN, value)
        editor.apply()
    }
// Get isFirstTimeLaunch value

    fun isFirstTimeLaunch(): Boolean {

        return pref!!.getBoolean(IS_FIRST_TIME_LAUNCH, true)
    }

    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        editor!!.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        editor!!.commit()
    }

    fun setWelcomeSkip(welcome: String) {
        editor!!.putString("welcome", welcome)
        editor?.commit()
    }


    fun getWelcomeSkip(): String {
        return pref?.getString("welcome", "").toString()
    }


    fun setToken(token: String) {
        editor?.putString("token", token)
        editor?.commit()
    }

    fun getToken(): String {
        return pref?.getString("token", "null").toString()
    }
}*/
