package com.example.smartselfplanner.DailyToDo.DailyEditTodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartselfplanner.Database.UserTask
import com.example.smartselfplanner.Database.UserTaskDatabaseDao

class DailyEditTodoViewModel(
    val userTask: UserTask,
    val database: UserTaskDatabaseDao
) : AndroidViewModel(Application()) {




    val testing = userTask.Task

}