package com.abdhilabs.mytask.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String?,
    val deadline: String?,
    val desc: String?
)