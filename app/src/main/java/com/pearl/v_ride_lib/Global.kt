package com.pearl.v_ride_lib

import android.content.Context
import android.content.res.Resources

object Global {
       var imageString: String = ""
       var CODE = 1
       var curr_lat: Double = 0.0
       var curr_long: Double = 0.0

       fun language(context2:Context,resourcess2:Resources):Resources{
              var contexts=context2
              var resourcess=resourcess2

              if (SessionManager.getLanguage(contexts) != null) {
                     if (SessionManager.getLanguage(contexts)?.length != 0) {
                            contexts = SessionManager.setLocale(contexts,SessionManager.getLanguage(contexts)!!
                            )
                     } else {
                            contexts = SessionManager.setLocale(contexts, "en")
                     }
              }else{
                     contexts = SessionManager.setLocale(contexts, "en")

              }

              resourcess = contexts.resources
              return resourcess
       }
}
