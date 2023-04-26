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
 fun setPerm(set: String) {
     editor?.putString("set",set)
     editor?.commit()

 }

     fun getPerm(): String {
         return pref?.getString("set","null").toString()
     }
     fun setLogin(Login: String) {
         editor?.putString("set",Login)
         editor?.commit()

     }

     fun getLogin(): String {
         return pref?.getString("Login","null").toString()
     }

     fun isLovPer():Boolean{
         return pref?.getBoolean("perm1",false)!!
     }

     fun setFile(status:Boolean){
         editor?.putBoolean("perm2",status)
         editor?.commit()
     }
     fun isFile():Boolean{
         return pref?.getBoolean("perm2",false)!!
     }
     fun setC(Login: String) {
         editor?.putString("cou",Login)
         editor?.commit()

     }

     fun getC(): String {
         return pref?.getString("cou","null").toString()
     }


     fun setWelcomeScreen(welcome: Boolean) {
         editor!!.putBoolean("welcome", welcome)
         editor!!.commit()
     }

     fun getWelcomeScreen(): Boolean {
         return pref!!.getBoolean("welcome", true)
     }

     fun setLocPer(welcome: Boolean) {
         editor!!.putBoolean("locper", welcome)
         editor!!.commit()
     }

     fun getLocPer(): Boolean {
         return pref!!.getBoolean("locper", false)
     }

     fun setReadPer(welcome: Boolean) {
         editor!!.putBoolean("readperm", welcome)
         editor!!.commit()
     }

     fun getReadPer(): Boolean {
         return pref!!.getBoolean("readperm", false)
     }
     fun setCallPer(welcome: Boolean) {
         editor!!.putBoolean("callperm", welcome)
         editor!!.commit()
     }

     fun getCallPer(): Boolean {
         return pref!!.getBoolean("callperm", false)
     }




     fun setLoc(welcome: Boolean) {
         editor!!.putBoolean("loc", welcome)
         editor!!.commit()
     }

     fun getLoc(): Boolean {
         return pref!!.getBoolean("loc", false)
     }

     fun setRead(welcome: Boolean) {
         editor!!.putBoolean("read", welcome)
         editor!!.commit()
     }

     fun getRead(): Boolean {
         return pref!!.getBoolean("read", false)
     }
     fun setCall(welcome: Boolean) {
         editor!!.putBoolean("call", welcome)
         editor!!.commit()
     }

     fun getCall(): Boolean {
         return pref!!.getBoolean("call", false)
     }
     fun setSetting(welcome: Boolean) {
         editor!!.putBoolean("setting", welcome)
         editor!!.commit()
     }

     fun getSetting(): Boolean {
         return pref!!.getBoolean("setting", false)
     }
 }