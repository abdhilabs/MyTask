package com.abdhilabs.mytask.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "task")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String?,
    val deadline: String?,
    val desc: String?
) : Parcelable