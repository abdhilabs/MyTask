package com.abdhilabs.mytask.di.component

import android.content.Context
import com.abdhilabs.mytask.base.BaseViewModelFactory
import com.abdhilabs.mytask.di.module.RoomModule
import com.abdhilabs.mytask.di.module.ServiceModule
import com.abdhilabs.mytask.receiver.TaskReceiver
import com.abdhilabs.mytask.viewmodel.TaskViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class, ServiceModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): AppComponent
    }

    fun taskViewModelFactory(): BaseViewModelFactory<TaskViewModel>
    fun inject(service: TaskReceiver)
}