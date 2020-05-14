package com.abdhilabs.mytask.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abdhilabs.mytask.data.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Query("SELECT * FROM task")
    fun getAllTask(): LiveData<List<Task>>

    @Delete
    suspend fun deleteTask(task: Task)
}