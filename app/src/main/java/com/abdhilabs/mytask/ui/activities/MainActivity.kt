package com.abdhilabs.mytask.ui.activities

import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.base.BaseActivity
import com.abdhilabs.mytask.databinding.ActivityMainBinding
import com.abdhilabs.mytask.ui.fragment.TaskFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun resourceLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        binding.lifecycleOwner = this
        binding.activity = this
    }

    fun onAddButtonClicked() {
        val bottomSheet = TaskFragment()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }
}
