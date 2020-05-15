package com.abdhilabs.mytask

import android.app.Application
import com.abdhilabs.mytask.di.DaggerComponentProvider
import com.abdhilabs.mytask.di.component.AppComponent
import com.abdhilabs.mytask.di.component.DaggerAppComponent

class App : Application(), DaggerComponentProvider {

    override val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .applicationContext(this)
            .build()
    }
}