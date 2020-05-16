package com.abdhilabs.mytask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdhilabs.mytask.data.model.Task
import com.abdhilabs.mytask.data.repository.TaskRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskViewModel @Inject constructor(private val repo: TaskRepository) : ViewModel() {

    private val _task = MutableLiveData<List<Task>?>()

    val task: LiveData<List<Task>?>
        get() = _task

    private val _isEmpty = MutableLiveData<Boolean>()

    val isEmpty: LiveData<Boolean>
        get() = _isEmpty

    init {
        getTask()
    }

    private fun getTask() = viewModelScope.launch {
        repo.getTask().collect { data ->
            if (data.isNotEmpty()) {
                _task.value = data
                _isEmpty.value = false
            } else {
                _isEmpty.value = true
            }
        }
    }

    fun saveTask(task: Task) = viewModelScope.launch {
        repo.insertTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        repo.deleteTask(task)
    }
}