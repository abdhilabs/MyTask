package com.abdhilabs.mytask.ui.activities

import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.abstraction.BaseActivity
import com.abdhilabs.mytask.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun resourceLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        AndroidInjection.inject(this)

    }

}
