package com.abdhilabs.mytask.di.module

import com.abdhilabs.mytask.ui.fragment.AddTaskFragment
import com.abdhilabs.mytask.ui.fragment.DetailFragment
import com.abdhilabs.mytask.ui.fragment.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjectionModule {

    @ContributesAndroidInjector
    abstract fun contributesHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributesAddTaskFragment(): AddTaskFragment

    @ContributesAndroidInjector
    abstract fun contributesDetailFragment(): DetailFragment
}