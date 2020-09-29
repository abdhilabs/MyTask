package com.abdhilabs.mytask.ui.fragment

import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.abstraction.BaseFragment
import com.abdhilabs.mytask.databinding.FragmentHomeBinding
import com.abdhilabs.mytask.viewmodel.TaskViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, TaskViewModel>() {

    override fun layoutResourceId(): Int = R.layout.fragment_home

    override fun getViewModelClass(): Class<TaskViewModel> = TaskViewModel::class.java

    override fun initViewCreated() {

    }
}