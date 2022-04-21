package com.example.smartselfplanner.Database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user_task_table")
@Parcelize
data class UserTask(
    @PrimaryKey(autoGenerate = true)
    var TaskId: Long = 0L,

    @ColumnInfo(name = "task_name")
    var Task: String = "",

    @ColumnInfo(name = "task_type")
    val TaskType: String = "",

   @ColumnInfo(name = "task_completed")
    var TaskCompleted: Boolean? = null



) : Parcelable