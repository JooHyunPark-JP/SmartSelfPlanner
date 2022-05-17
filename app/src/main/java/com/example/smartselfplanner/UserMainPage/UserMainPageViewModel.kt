package com.example.smartselfplanner.UserMainPage

import android.app.Application
import androidx.lifecycle.*
import com.example.smartselfplanner.Database.UserTaskDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserMainPageViewModel(
    val database: UserTaskDatabaseDao,
    application: Application
) : AndroidViewModel(Application()) {
    // private val context = getApplication<Application>().applicationContext

    val todoCount = database.getTodoCount("UserTodo")
    val dailyTodoCount = database.getTodoCount("UserDailyToDo")
    val getDailyLeftTodoCount = database.getDailyLeftTodoCount("UserDailyToDo", false)


    //Testing purpose
    fun onClear() {
        viewModelScope.launch {
            clear()
            // recentWifi.value = null
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }


}

