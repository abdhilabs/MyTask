package com.abdhilabs.mytask.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abdhilabs.mytask.data.model.Task
import com.abdhilabs.mytask.utils.VERSION_DB

@Database(entities = [Task::class], version = VERSION_DB)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}