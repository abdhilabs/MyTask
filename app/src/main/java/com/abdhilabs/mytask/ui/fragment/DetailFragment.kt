package com.abdhilabs.mytask.ui.fragment

import android.view.ViewGroup.LayoutParams
import android.view.WindowManager
import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.base.BaseDialogFragment
import com.abdhilabs.mytask.data.model.Task
import com.abdhilabs.mytask.databinding.ItemDialogTaskInfoBinding

class DetailFragment(val task: Task) : BaseDialogFragment<ItemDialogTaskInfoBinding>() {

    override fun layoutResourceId(): Int = R.layout.item_dialog_task_info

    override fun initViewCreated() {
        binding.lifecycleOwner = this
        binding.fragment = this
        with(binding) {
            tvDialogTitle.text = task.title
            tvDialogDeadline.text = resources.getString(R.string.text_deadline, task.deadline)
            tvDialogDesc.text = resources.getString(R.string.text_description, task.desc)
        }

    }

    override fun onResume() {
        super.onResume()
        val params: LayoutParams = dialog!!.window!!.attributes
        params.width = LayoutParams.MATCH_PARENT
        params.height = LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    fun onEditButtonClicked() {
        val bottomSheet = AddTaskFragment(task)
        bottomSheet.show(childFragmentManager, bottomSheet.tag)
        this.dismiss()
    }
}