package com.abdhilabs.mytask.data.repository

import com.abdhilabs.mytask.data.model.Task
import com.abdhilabs.mytask.db.AppDatabase

class TaskRepository(private val db: AppDatabase) {

    suspend fun insertTask(task: Task) = db.taskDao().insertTask(task)

    fun getTask() = db.taskDao().getAllTask()

    suspend fun deleteTask(task: Task) = db.taskDao().deleteTask(task)
}