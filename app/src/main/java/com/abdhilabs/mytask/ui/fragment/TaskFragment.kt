package com.abdhilabs.mytask.ui.fragment

import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.base.BaseDialogFragment
import com.abdhilabs.mytask.databinding.BottomSheetTaskBinding

class TaskFragment : BaseDialogFragment<BottomSheetTaskBinding>() {

    override fun layoutResourceId(): Int = R.layout.bottom_sheet_task

    override fun initViewCreated() {
        binding.lifecycleOwner = this
        binding.fragment = this

    }
}