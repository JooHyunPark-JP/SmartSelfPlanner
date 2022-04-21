package com.example.smartselfplanner.UserMainPage

import android.app.Application
import androidx.lifecycle.*
import com.example.smartselfplanner.Database.UserTaskDatabaseDao

class UserMainPageViewModel (
    val database: UserTaskDatabaseDao,
    application: Application) : AndroidViewModel(Application()) {


   // private val context = getApplication<Application>().applicationContext


     val todoCount = database.getTodoCount("UserTodo")


    }

