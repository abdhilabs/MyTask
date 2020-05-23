package com.abdhilabs.mytask.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abdhilabs.mytask.App.Companion.pref
import com.abdhilabs.mytask.data.model.Task
import com.abdhilabs.mytask.data.repository.TaskRepository
import com.abdhilabs.mytask.utils.cancelNotification
import com.abdhilabs.mytask.utils.setupNotification
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    private val repo: TaskRepository,
    private val app: Application
) : AndroidViewModel(app) {

    private val _task = MutableLiveData<List<Task>?>()

    val task: LiveData<List<Task>?>
        get() = _task

    private val _isEmpty = MutableLiveData<Boolean>()

    val isEmpty: LiveData<Boolean>
        get() = _isEmpty

    private val _isChecked = MutableLiveData<Boolean>()

    val isChecked: LiveData<Boolean>
        get() = _isChecked

    init {
        _isChecked.value = pref.isChecked
        getTask()
    }

    fun setAlarm(isChecked: Boolean) {
        when (isChecked) {
            true -> setupNotification(app)
            false -> cancelNotification(app)
        }
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