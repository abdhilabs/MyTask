package com.abdhilabs.mytask.di.module

import com.abdhilabs.mytask.data.dispatcher.CoroutineDispatcherProvider
import com.abdhilabs.mytask.data.dispatcher.DispatcherProvider
import dagger.Binds
import dagger.Module

@Module
interface CoroutineDispatcherModule {
    @Binds
    fun bindDispatcher(dispatcherProvider: CoroutineDispatcherProvider): DispatcherProvider
}