package com.example.smartselfplanner.DailyToDo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartselfplanner.Database.UserTaskDatabaseDao
import com.example.smartselfplanner.UserMainPage.UserMainPageViewModel

class DailyTodoViewModelFactory(
    private val dataSource: UserTaskDatabaseDao,
    private val application: Application
) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyTodoViewModel::class.java)) {
            return DailyTodoViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
