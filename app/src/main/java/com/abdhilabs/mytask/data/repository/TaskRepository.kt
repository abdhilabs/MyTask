package com.abdhilabs.mytask.data.repository

import com.abdhilabs.mytask.data.model.Task
import com.abdhilabs.mytask.db.AppDatabase
import dagger.Reusable
import javax.inject.Inject

@Reusable
class TaskRepository @Inject constructor(private val db: AppDatabase) {

    suspend fun insertTask(task: Task) = db.taskDao().insertTask(task)

    suspend fun updateTask(task: Task) = db.taskDao().updateTask(task)

    fun getTask() = db.taskDao().getAllTask()

    suspend fun deleteTask(task: Task) = db.taskDao().deleteTask(task)
}