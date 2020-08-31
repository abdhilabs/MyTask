package com.abdhilabs.mytask.di.component

import com.abdhilabs.mytask.App
import com.abdhilabs.mytask.di.module.*
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityInjectionModule::class,
        FragmentInjectionModule::class,
        ContextModule::class,
        CoroutineDispatcherModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        SharedPreferenceModule::class,
        RoomModule::class,
        ServiceModule::class]
)
interface AppComponent {
    fun inject(instance: App)
}