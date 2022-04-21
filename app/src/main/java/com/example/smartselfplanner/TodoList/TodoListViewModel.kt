package com.example.smartselfplanner.TodoList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartselfplanner.Database.UserTask
import com.example.smartselfplanner.Database.UserTaskDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoListViewModel (
    val database: UserTaskDatabaseDao,
    application: Application
) : AndroidViewModel(Application()) {


    val todoList = database.getTodoTask("UserTodo")


    fun onEditSelected(userTask: UserTask) = viewModelScope.launch {
        update(userTask)
    }

    private suspend fun update(task:UserTask){
        withContext(Dispatchers.IO){
            database.update(task)
        }
    }

    fun onDeleteSelected(userTask: UserTask) = viewModelScope.launch {
        deleteRow(userTask)
    }

    private suspend fun deleteRow(task:UserTask){
        withContext(Dispatchers.IO){
            database.deleteRow(task.TaskId)
        }
    }
}