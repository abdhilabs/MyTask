package com.abdhilabs.mytask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdhilabs.mytask.data.model.Task
import com.abdhilabs.mytask.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repo: TaskRepository) : ViewModel() {

    fun saveTask(task: Task) = viewModelScope.launch {
        repo.insertTask(task)
    }

    fun getTask() = repo.getTask()

    fun deleteTask(task: Task) = viewModelScope.launch {
        repo.deleteTask(task)
    }
}