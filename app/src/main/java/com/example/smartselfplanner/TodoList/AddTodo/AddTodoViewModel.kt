package com.example.smartselfplanner.TodoList.AddTodo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartselfplanner.Database.UserTask
import com.example.smartselfplanner.Database.UserTaskDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTodoViewModel(
    val database: UserTaskDatabaseDao
) : ViewModel() {


    fun addTodo(toDo: String) {
        viewModelScope.launch {
            val newTodo = UserTask(TaskType = "UserTodo", Task = toDo, isChecked = false)
            insert(newTodo)
            // recentTodo.value = getRecentWifiFromDatabase()
        }
    }

    private suspend fun insert(userTask: UserTask) {
        withContext(Dispatchers.IO) {
            database.insert(userTask)
        }
    }
}