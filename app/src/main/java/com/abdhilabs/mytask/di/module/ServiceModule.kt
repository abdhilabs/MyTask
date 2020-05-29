package com.abdhilabs.mytask.di.module

import android.content.BroadcastReceiver
import com.abdhilabs.mytask.receiver.TaskReceiver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideService(service: TaskReceiver): BroadcastReceiver {
        return service
    }
}