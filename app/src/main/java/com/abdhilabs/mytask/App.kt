package com.abdhilabs.mytask

import android.app.Application
import com.abdhilabs.mytask.di.component.AppComponent
import com.abdhilabs.mytask.di.component.DaggerAppComponent
import com.abdhilabs.mytask.di.module.ContextModule
import com.abdhilabs.mytask.di.module.RoomModule
import com.abdhilabs.mytask.di.module.ServiceModule
import com.abdhilabs.mytask.di.module.SharedPreferenceModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppDI()
    }

    private fun initAppDI() {
        appComponent = DaggerAppComponent
            .builder()
            .contextModule(ContextModule(this))
            .roomModule(RoomModule())
            .serviceModule(ServiceModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .build()
        appComponent.inject(this)
    }

    open fun getComponent(): AppComponent {
        return appComponent
    }
}
