package com.abdhilabs.mytask.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdhilabs.mytask.di.annotation.ViewModelKey
import com.abdhilabs.mytask.di.factory.ViewModelFactory
import com.abdhilabs.mytask.viewmodel.TaskViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TaskViewModel::class)
    abstract fun provideAuthViewModel(viewModel: TaskViewModel): ViewModel

}