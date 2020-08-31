package com.abdhilabs.mytask.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.abdhilabs.mytask.data.repository.TaskRepository
import com.abdhilabs.mytask.db.AppDatabase
import com.abdhilabs.mytask.db.TaskDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDb(applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java, "task.db")
            .build()
    }

    @Singleton
    @Provides
    fun taskDao(db: AppDatabase): TaskDao {
        return db.taskDao()
    }

    @Singleton
    @Provides
    fun taskRepository(db: AppDatabase): TaskRepository {
        return TaskRepository(db)
    }
}