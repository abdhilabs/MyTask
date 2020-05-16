package com.abdhilabs.mytask.db

import androidx.room.*
import com.abdhilabs.mytask.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Query("SELECT * FROM task")
    fun getAllTask(): Flow<List<Task>>

    @Delete
    suspend fun deleteTask(task: Task)
}