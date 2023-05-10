package com.pearl.v_ride_lib

import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import java.util.Locale

object SessionManager {
    private const val Selected_Language = "Language"
    fun onChanged(context: Context?, defaultLanguages: String?): Context {
        val lang = getSaveData(context,defaultLanguages);
        return setLocale(context, lang.toString())
    }

    private fun getSaveData(context: Context?, defaultLanguages: String?): String? {

        val pref = PreferenceManager.getDefaultSharedPreferences(context)

        return pref.getString(Selected_Language,defaultLanguages)
    }

    fun setLocale(context: Context?, lang: String): Context {

        persist(context,lang)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return updateResources(context,lang)
        }
        return updateResourcesleg(context,lang)

    }

    private fun updateResources(context: Context?, lang: String): Context {

        val locale = Locale(lang)
        Locale.setDefault(locale)
        var conf = context?.resources?.configuration

        conf?.locale = locale

        conf?.setLayoutDirection(locale)
        return context?.createConfigurationContext(conf!!)!!
    }

    private fun updateResourcesleg(context: Context?, lang: String): Context {

        val locale = Locale(lang)
        Locale.setDefault(locale)
        val resource = context?.resources
        val conf = resource?.configuration
        conf?.setLocale(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf?.setLayoutDirection(locale)
        }
        resource?.updateConfiguration(conf,resource.displayMetrics)
        return context!!
    }

    private fun persist(context: Context?, lang: String) {

        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putString(Selected_Language,lang)
        editor.apply()
    }

    fun getLanguage(context: Context): String? {
        return getSaveData(context,Locale.getDefault().language)
    }
}