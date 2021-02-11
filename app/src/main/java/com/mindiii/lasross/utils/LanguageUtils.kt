package com.mindiii.lasross.utils

import android.app.Activity
import android.content.Intent
import com.mindiii.lasross.module.settings.SettingActivity

import android.content.res.Configuration
import java.util.Locale

class LanguageUtils {
    companion object {
        fun language(activity: Activity, lang:String,isRecreate:Boolean) {
        //    val locale = Locale(lang)
          //  val config = activity.resources.configuration
            /*config.setLayoutDirection(locale)
            config.locale = locale
            activity.resources
                    .updateConfiguration(config, activity.resources.displayMetrics)*/
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            activity.getResources().updateConfiguration(config,
                activity.getResources().getDisplayMetrics())
            if (isRecreate){
                val intent=Intent(activity,SettingActivity::class.java)
                 activity.finish()
                activity.startActivity(intent)
            }
            // activity.recreate()
        }
    }
}