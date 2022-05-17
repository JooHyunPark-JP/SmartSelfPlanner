package com.example.smartselfplanner.DailyToDo.DailyAddTodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartselfplanner.DailyToDo.DailyTodoViewModel
import com.example.smartselfplanner.Database.UserTaskDatabaseDao

class DailyAddTodoViewModelFactory(private val dataSource: UserTaskDatabaseDao) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyAddTodoViewModel::class.java)) {
            return DailyAddTodoViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}