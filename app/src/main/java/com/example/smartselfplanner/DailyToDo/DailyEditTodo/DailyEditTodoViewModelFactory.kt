package com.example.smartselfplanner.DailyToDo.DailyEditTodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartselfplanner.DailyToDo.DailyTodoViewModel
import com.example.smartselfplanner.Database.UserTask
import com.example.smartselfplanner.Database.UserTaskDatabaseDao

class DailyEditTodoViewModelFactory(
    private val userTask: UserTask,
    private val dataSource: UserTaskDatabaseDao
) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyEditTodoViewModel::class.java)) {
            return DailyEditTodoViewModel(userTask, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}