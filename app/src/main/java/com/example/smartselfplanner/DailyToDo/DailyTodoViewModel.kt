package com.example.smartselfplanner.DailyToDo

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartselfplanner.Database.UserTask
import com.example.smartselfplanner.Database.UserTaskDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DailyTodoViewModel(
    val database: UserTaskDatabaseDao,
) : AndroidViewModel(Application()) {

    private val milliseconds: Int = 2073600000
    private var hour: Int = 0
    private var minutes: Int = 0

    val dailyTodoList = database.getTodoTask("UserDailyToDo")




    fun onDeleteSelected(userTask: UserTask) = viewModelScope.launch {
        deleteRow(userTask)
    }

    private suspend fun deleteRow(task: UserTask){
        withContext(Dispatchers.IO){
            database.deleteRow(task.TaskId)
        }
    }

    fun onSetTaskCompleted(userTask: UserTask) = viewModelScope.launch {
        TaskCompleted(userTask)
      //  Log.d("userTask", "onSetTaskCompleted: " + userTask.TaskId+ userTask.TaskCompleted)
    }

    private suspend fun TaskCompleted(task: UserTask) {
         withContext(Dispatchers.IO){
            database.update(task)
        }
    }

    private fun setResetDateTimer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            // val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
            val localTimeHourFormatter = DateTimeFormatter.ofPattern("HH")
            val localTimeMinuteFormatter = DateTimeFormatter.ofPattern("mm")
            val hourFormatter = current.format(localTimeHourFormatter)
            val minuteFormatter = current.format(localTimeMinuteFormatter)
            val currentLocalTimeInMilis =
                (hourFormatter.toInt() * 60 * 60 * 1000 * 24) + (minuteFormatter.toInt() * 1000 * 60)
            val currentLocalTime = milliseconds - currentLocalTimeInMilis
            hour = currentLocalTime / 1000 / 60 / 60 / 24
            minutes =
                60 - ((currentLocalTimeInMilis - (hourFormatter.toInt() * 60 * 60 * 1000 * 24)) / 1000 / 60)

        } else {
            var date = Date()
            //  val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
            val localTimeHourFormatter = SimpleDateFormat("HH")
            val localTimeMinuteFormatter = SimpleDateFormat("mm")
            val hourFormatter = localTimeHourFormatter.format(date)
            val minuteFormatter = localTimeMinuteFormatter.format(date)
            val currentLocalTimeInMilis =
                (hourFormatter.toInt() * 60 * 60 * 1000 * 24) + (minuteFormatter.toInt() * 1000 * 60)
            val currentLocalTime = milliseconds - currentLocalTimeInMilis
            hour = currentLocalTime / 1000 / 60 / 60 / 24
            minutes =
                60 - ((currentLocalTimeInMilis - (hourFormatter.toInt() * 60 * 60 * 1000 * 24)) / 1000 / 60)
        }
    }
}