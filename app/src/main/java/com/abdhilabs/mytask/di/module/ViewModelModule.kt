package com.abdhilabs.mytask.di.module

import androidx.lifecycle.ViewModelProvider
import com.abdhilabs.mytask.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

//    @Binds
//    @IntoMap
//    @ViewModelKey(AuthViewModel::class)
//    abstract fun provideAuthViewModel(viewModel: AuthViewModel): ViewModel

}