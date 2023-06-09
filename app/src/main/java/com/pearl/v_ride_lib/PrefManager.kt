package com.pearl.v_ride_lib

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


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

     fun setPr(Login: Boolean) {
         editor?.putBoolean("pr",Login)
         editor?.commit()
     }
     fun getPr(): Boolean {
         return pref!!.getBoolean("pr",false)
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
     fun setCall(welcome: Boolean) {
         editor!!.putBoolean("call", welcome)
         editor!!.commit()
     }
     fun setLangauge(id:String){
         editor!!.putString("lanID", id)
         editor!!.commit()
     }
     fun getLanID():String{
         return pref?.getString("lanID", "en").toString()
     }
     fun setToken(token: String){
         editor!!.putString("token", token)
         editor!!.commit()
     }
     fun getToken(): String{
         return  pref?.getString("token","").toString()
     }
     fun setCode(code1: Int){
         editor!!.putInt("code1", code1)
         editor!!.commit()
     }
     fun getCode(): Int{
         return  pref?.getInt("code1",0)!!.toInt()
     }
     fun setIds(id: String) {
         editor!!.putString("id",id)
         editor!!.commit()
     }
     fun getIds(): String {
         return pref?.getString("id", "id").toString()
     }

     fun setList(list: List<String>?) {
         val gson = Gson()
         val listJson = gson.toJson(list)
         editor?.putString("list_key", listJson)
         editor?.commit()
     }

     fun getList(): List<String> {
         val gson = Gson()
         val listJson = pref?.getString("list_key", null)
         if (listJson != null) {
             val type = object : TypeToken<List<Int>>() {}.type
             return gson.fromJson(listJson, type)
         }
         return emptyList()
     }

    fun setlatitude(lat:Double){
        editor?.putFloat("lat", lat.toFloat())
        editor?.commit()
    }
    fun getlatitude():Double{
        return  pref?.getFloat("lat",0.0f)!!.toDouble()
    }
    fun setlongitude(long:Double){
        editor?.putFloat("long",long.toFloat())
        editor?.commit()
    }
    fun getlongitude():Double{
        return  pref?.getFloat("long",0.0f)!!.toDouble()
    }

    fun setNotificationToken(token: String){
        editor!!.putString("tkn", token)
        editor!!.commit()
    }
    fun getNotificationToken(): String {
        return pref?.getString("tkn","").toString()
    }

    fun setStationSerialNumber(serialNo: String) {
        editor!!.putString("serialNo",serialNo)
        editor!!.commit()
    }
    fun getStationSerialNumber(): String {
        return pref?.getString("serialNo", null).toString()
    }

 }

