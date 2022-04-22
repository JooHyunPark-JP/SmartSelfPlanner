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
) : ViewModel()  {

   // val taskType = userTask.TaskType

    //Create a viewmodeljob for coroutine
    private var viewModelJob = Job()

    private var recentTask = MutableLiveData<UserTask?>()
    val _recentTodo: MutableLiveData<UserTask?>
        get()= recentTask


    init{
       // Log.d("taskType", "currenT taskType: " + taskType)
        // initializeTodoList()
    }

    fun addTodo(toDo: String)  {
        viewModelScope.launch {
            val newTodo = UserTask(TaskType = "UserTodo", Task = toDo, isChecked = false)
            insert(newTodo)
            // recentTodo.value = getRecentWifiFromDatabase()
        }
    }

/*    private suspend fun getRecentWifiFromDatabase(): WifiAndTodo? {
        return withContext(Dispatchers.IO) {
            var todo = database.getMostRecentWifiData()

            todo
        }
    }*/

    private suspend fun insert(userTask: UserTask) {
        withContext(Dispatchers.IO) {
            database.insert(userTask)
        }
    }
}