package com.abdhilabs.mytask.di.module

import android.content.Context
import android.content.SharedPreferences
import com.abdhilabs.mytask.utils.PrefManager
import com.abdhilabs.mytask.utils.PrefManager.Companion.PREF_NAME
import dagger.Module
import dagger.Provides

@Module
class SharedPreferenceModule {

    @Provides
    fun providePrefManager(context: Context): PrefManager = PrefManager(context)

    @Provides
    fun providesPreference(context: Context): SharedPreferences = context.getSharedPreferences(
        PREF_NAME, Context.MODE_PRIVATE
    )

    @Provides
    fun providesEditor(sp: SharedPreferences): SharedPreferences.Editor = sp.edit()

}