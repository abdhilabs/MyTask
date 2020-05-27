package com.abdhilabs.mytask.di.module

import androidx.lifecycle.LifecycleService
import com.abdhilabs.mytask.service.TaskService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideService(service: TaskService): LifecycleService {
        return service
    }
}