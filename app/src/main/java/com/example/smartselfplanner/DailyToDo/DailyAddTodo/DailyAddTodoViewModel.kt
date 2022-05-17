package com.example.smartselfplanner.DailyToDo.DailyAddTodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartselfplanner.Database.UserTask
import com.example.smartselfplanner.Database.UserTaskDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DailyAddTodoViewModel(
    val database: UserTaskDatabaseDao,
) : AndroidViewModel(Application()) {


    fun addTodo(dailyToDo: String) {
        viewModelScope.launch {
            val newTodo =
                UserTask(TaskType = "UserDailyToDo", Task = dailyToDo, TaskCompleted = false)
            insert(newTodo)
        }
    }

    fun addTodoWithTimer(dailyToDo: String, hour: Int, min: Int, sec: Int) {
        viewModelScope.launch {
            val newTodo = UserTask(
                TaskType = "UserDailyToDo",
                Task = dailyToDo,
                TaskCompleted = false,
                dailyTimerHour = hour,
                dailyTimerMin = min,
                dailyTimerSec = sec
            )
            insert(newTodo)
        }
    }

    private suspend fun insert(userTask: UserTask) {
        withContext(Dispatchers.IO) {
            database.insert(userTask)
        }
    }


}