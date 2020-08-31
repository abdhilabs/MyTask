package com.abdhilabs.mytask.di.module

import android.content.Context
import com.abdhilabs.mytask.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(val app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context{
        return app
    }
}