package com.example.smartselfplanner.DailyToDo.DailyEditTodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartselfplanner.Database.UserTask
import com.example.smartselfplanner.Database.UserTaskDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DailyEditTodoViewModel(
    val userTask: UserTask,
    val database: UserTaskDatabaseDao
) : AndroidViewModel(Application()) {

    val userDailyTask = userTask.Task

    fun editDailyTask(task: String, hour: Int, min: Int, sec: Int) = viewModelScope.launch {
        val editTask = database.get(userTask.TaskId)
        editTask.Task = task
        editTask.dailyTimerHour = hour
        editTask.dailyTimerMin = min
        editTask.dailyTimerSec = sec
        update(editTask)
    }

    fun editDailyTaskWithoutTimer(task: String) = viewModelScope.launch {
        val editTask = database.get(userTask.TaskId)
        editTask.Task = task
        editTask.dailyTimerHour = null
        editTask.dailyTimerMin = null
        editTask.dailyTimerSec = null
        update(editTask)
    }

    private suspend fun update(task: UserTask) {
        withContext(Dispatchers.IO) {
            database.update(task)
        }
    }


}