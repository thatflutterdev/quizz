package com.pluralsight.commonintents

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class LocalizationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setLanguageForApp()
        setContentView(R.layout.activity_localization)

    }

    private fun setLanguageForApp(){
        val languageToLoad = "fr" //any language code e.g "en",
        var locale: Locale
        locale = if(languageToLoad.equals("not-set",true)) {
            Locale.getDefault()
        } else{
            Locale(languageToLoad)
        }

        Locale.setDefault(locale)

        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)


    }

}