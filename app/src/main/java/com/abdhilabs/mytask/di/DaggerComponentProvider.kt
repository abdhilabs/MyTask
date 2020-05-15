package com.abdhilabs.mytask.di

import android.app.Activity
import com.abdhilabs.mytask.di.component.AppComponent

interface DaggerComponentProvider {
    val appComponent: AppComponent
}

val Activity.injector: AppComponent get() = (application as DaggerComponentProvider).appComponent
//val Fragment.injector: AppComponent get() = (requireActivity().application as DaggerComponentProvider).appComponent