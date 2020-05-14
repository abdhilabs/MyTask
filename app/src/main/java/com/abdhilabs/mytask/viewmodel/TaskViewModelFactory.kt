package com.abdhilabs.mytask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdhilabs.mytask.data.repository.TaskRepository

class TaskViewModelFactory(private val repo: TaskRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}