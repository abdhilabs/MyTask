package com.abdhilabs.mytask.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.base.BaseDialogFragment
import com.abdhilabs.mytask.data.model.Task
import com.abdhilabs.mytask.databinding.BottomSheetTaskBinding
import com.abdhilabs.mytask.ui.activities.MainActivity
import com.abdhilabs.mytask.utils.DateTimeFormatter
import com.abdhilabs.mytask.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class TaskFragment : BaseDialogFragment<BottomSheetTaskBinding>() {

    private lateinit var viewmodel: TaskViewModel

    override fun layoutResourceId(): Int = R.layout.bottom_sheet_task

    override fun initViewCreated() {
        binding.lifecycleOwner = this
        binding.fragment = this
        viewmodel = (activity as MainActivity).viewmodel
    }

    @SuppressLint("SetTextI18n")
    fun onPickDateClicked() {
        val c = Calendar.getInstance()
        val years = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePicker = DatePickerDialog(
            requireContext(),
            R.style.CalendarDialog,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val date = DateTimeFormatter.getDateFromString("$dayOfMonth-${monthOfYear+1}-$year")
                binding.etDeadline.setText(date)
            },
            years,
            month,
            day
        )
        datePicker.show()

    }

    fun onBtnSaveClicked() {
        with(binding) {
            val title = etTitle.text.toString()
            val deadline = etDeadline.text.toString()
            val desc = etDescription.text.toString()
            val task = Task(title = title, deadline = deadline, desc = desc)

            if (title.isEmpty() || deadline.isEmpty() || desc.isEmpty()) when {
                title.isEmpty() -> {
                    etTitle.error = "Judul harus di isi"
                }
                deadline.isEmpty() -> {
                    etDeadline.error = "Deadline harus di isi"
                }
                desc.isEmpty() -> {
                    etDescription.error = "Deadline harus di isi"
                }
                else -> null
            } else {
                viewmodel.saveTask(task)
                Snackbar.make(binding.root, "Successfully added new task", Snackbar.LENGTH_LONG)
                    .show()
                (activity as MainActivity).bottomSheet.dismiss()
            }
        }
    }
}