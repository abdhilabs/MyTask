package com.abdhilabs.mytask.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abdhilabs.mytask.App.Companion.pref
import com.abdhilabs.mytask.data.model.Task
import com.abdhilabs.mytask.data.repository.TaskRepository
import com.abdhilabs.mytask.utils.DateTimeFormatter
import com.abdhilabs.mytask.utils.cancelNotification
import com.abdhilabs.mytask.utils.setupNotification
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    private val repo: TaskRepository,
    private val app: Application
) : AndroidViewModel(app) {

    private val now = Calendar.getInstance()
    private val date = DateTimeFormatter.getDate(now.time)

    private val _task = MutableLiveData<List<Task>?>()
    val task: LiveData<List<Task>?>
        get() = _task

    private val _messageSuccess = MutableLiveData<String?>()
    val messageSuccess: LiveData<String?>
        get() = _messageSuccess

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean>
        get() = _isEmpty

    private val _isChecked = MutableLiveData<Boolean>()
    val isChecked: LiveData<Boolean>
        get() = _isChecked

    private val _setTextDay = MutableLiveData<String>()
    val setTextDay: LiveData<String>
        get() = _setTextDay

    private val _setTextUsername = MutableLiveData<String>()
    val setTextUsername: LiveData<String>
        get() = _setTextUsername

    private val _setTextToday = MutableLiveData<String>()
    val setTextToday: LiveData<String>
        get() = _setTextToday

    init {
        _isChecked.value = pref.isChecked
        _setTextDay.value = getTextDay()
        _setTextUsername.value = "Abdhi"
        _setTextToday.value = date
        getTask()
    }

    private fun getTextDay(): String {
        val c = Calendar.getInstance()
        return when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Good Morning"
            in 12..15 -> "Good Afternoon"
            in 16..20 -> "Good Evening"
            in 21..23 -> "Good Night"
            else -> "Hello"
        }
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
        _messageSuccess.value = "Successfully added new task"
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repo.updateTask(task)
        _messageSuccess.value = "Successfully edited task"
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        repo.deleteTask(task)
    }
}