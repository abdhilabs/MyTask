package com.abdhilabs.mytask

import android.app.Application
import com.abdhilabs.mytask.di.DaggerComponentProvider
import com.abdhilabs.mytask.di.component.AppComponent
import com.abdhilabs.mytask.di.component.DaggerAppComponent
import com.abdhilabs.mytask.utils.PrefManager

class App : Application(), DaggerComponentProvider {

    companion object {
        lateinit var pref: PrefManager
    }

    override val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .applicationContext(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        pref = PrefManager(this)
    }
}