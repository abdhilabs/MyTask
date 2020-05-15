package com.abdhilabs.mytask.di.module

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdhilabs.mytask.data.repository.TaskRepository
import com.abdhilabs.mytask.db.AppDatabase
import com.abdhilabs.mytask.db.TaskDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideRoomDb(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "task.db")
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