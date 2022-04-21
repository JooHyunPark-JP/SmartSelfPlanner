package com.example.smartselfplanner.TodoList.AddTodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartselfplanner.Database.UserTaskDatabaseDao

class AddTodoViewModelFactory (
    private val dataSource: UserTaskDatabaseDao,
) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTodoViewModel::class.java)) {
            return AddTodoViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}