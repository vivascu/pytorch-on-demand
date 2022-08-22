package ch.vivascu.dynamic.dynamicnativelib

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import com.google.android.play.core.splitcompat.SplitCompat
import java.util.*

class MyApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}