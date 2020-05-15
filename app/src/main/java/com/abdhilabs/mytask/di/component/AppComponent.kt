package com.abdhilabs.mytask.di.component

import android.app.Application
import com.abdhilabs.mytask.base.BaseViewModelFactory
import com.abdhilabs.mytask.di.module.RoomModule
import com.abdhilabs.mytask.viewmodel.TaskViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(application: Application): Builder

        fun build(): AppComponent
    }

    fun taskViewModelFactory(): BaseViewModelFactory<TaskViewModel>

}