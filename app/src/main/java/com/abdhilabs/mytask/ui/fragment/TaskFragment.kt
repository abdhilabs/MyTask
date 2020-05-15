package com.abdhilabs.mytask.ui.fragment

import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.base.BaseDialogFragment
import com.abdhilabs.mytask.data.model.Task
import com.abdhilabs.mytask.databinding.BottomSheetTaskBinding
import com.abdhilabs.mytask.ui.activities.MainActivity
import com.abdhilabs.mytask.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar

class TaskFragment : BaseDialogFragment<BottomSheetTaskBinding>() {

    private lateinit var viewmodel: TaskViewModel

    override fun layoutResourceId(): Int = R.layout.bottom_sheet_task

    override fun initViewCreated() {
        binding.lifecycleOwner = this
        binding.fragment = this
        viewmodel = (activity as MainActivity).viewmodel
    }

    fun onBtnSaveClicked() {
        with(binding) {
            val title = etTitle.text.toString()
            val deadline = etDeadline.text.toString()
            val desc = etDescription.text.toString()
            val task = Task(title = title, deadline = deadline, desc = desc)
            viewmodel.saveTask(task)
        }
        Snackbar.make(binding.root, "Successfully added new task", Snackbar.LENGTH_LONG).show()
        (activity as MainActivity).bottomSheet.dismiss()
    }
}