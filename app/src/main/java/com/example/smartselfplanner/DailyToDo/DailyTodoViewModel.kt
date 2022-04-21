package com.example.smartselfplanner.DailyToDo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.smartselfplanner.Database.UserTaskDatabaseDao

class DailyTodoViewModel(
    val database: UserTaskDatabaseDao,
) : AndroidViewModel(Application()) {


}