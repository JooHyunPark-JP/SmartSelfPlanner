package com.example.smartselfplanner.TodoList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.smartselfplanner.Database.UserTaskDatabaseDao

class TodoListViewModel (
    val database: UserTaskDatabaseDao,
    application: Application
) : AndroidViewModel(Application()) {


    val todoList = database.getTodoTask("UserTodo")
}