package com.example.smartselfplanner.DailyToDo

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smartselfplanner.Database.UserTask
import com.example.smartselfplanner.Database.UserTaskDatabaseDao
import com.example.smartselfplanner.R
import com.example.smartselfplanner.Receiver.AlarmReceiver
import com.example.smartselfplanner.utils.cancelNotifications
import com.example.smartselfplanner.utils.sendNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DailyTodoViewModel(
    val database: UserTaskDatabaseDao,
    private val app: Application
) : AndroidViewModel(app) {

    val dailyTodoList = database.getTodoTask("UserDailyToDo")

    private val REQUEST_CODE = 0
    private val TRIGGER_TIME = "TRIGGER_AT"

    var hour : Int = 0
    var min : Int = 0
    var sec : Int = 0

    private val hourTimer: Long = 3600000L
    private val minute: Long = 60_000L
    private val second: Long = 1_000L

    private val notifyPendingIntent: PendingIntent

    private val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private var prefs =
        app.getSharedPreferences("com.example.smartselfplanner", Context.MODE_PRIVATE)
    private val notifyIntent = Intent(app, AlarmReceiver::class.java)



    private val _elapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long>
        get() = _elapsedTime

    private var _alarmOn = MutableLiveData<Boolean>()
    val isAlarmOn: LiveData<Boolean>
        get() = _alarmOn

    private val tasksEventChannel = Channel<TasksEvent>()
    val tasksEvent = tasksEventChannel.receiveAsFlow()

    private lateinit var timer: CountDownTimer

    init{
        _alarmOn.value = PendingIntent.getBroadcast(
            getApplication(),
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_NO_CREATE
        ) != null

        Log.d("alarmon",  _alarmOn.value.toString())

        notifyPendingIntent = PendingIntent.getBroadcast(
            getApplication(),
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        Log.d("nitifyintent",  notifyPendingIntent.toString())


        //If alarm is not null, resume the timer back for this alarm
        if (_alarmOn.value!!) {
            createTimer()
        }

    }

    private fun createTimer() {
        viewModelScope.launch {
            val triggerTime = loadTime()
            timer = object : CountDownTimer(triggerTime, second) {
                override fun onTick(millisUntilFinished: Long) {
                    _elapsedTime.value = triggerTime - SystemClock.elapsedRealtime()
                    if (_elapsedTime.value!! <= 0) {
                        resetTimer()
                    }
                }
                override fun onFinish() {
                    resetTimer()
                }
            }
            timer.start()
        }
    }

    private suspend fun loadTime(): Long =
        withContext(Dispatchers.IO) {
            prefs.getLong(TRIGGER_TIME, 0)
        }

    /**
     * Resets the timer on screen and sets alarm value false
     */
    private fun resetTimer() {
        timer.cancel()
        _elapsedTime.value = 0
        _alarmOn.value = false
    }

    fun setAlarm(isChecked: Boolean, userTask: UserTask) {
        hour = userTask.dailyTimerHour!!
        min = userTask.dailyTimerMin!!
        sec = userTask.dailyTimerSec!!
        Log.d("AlarmTesting", "$hour + $min + $sec")
        when (isChecked) {
            /*true -> timeSelection.value?.let { startTimer(it) }*/
            true -> startTimer(hour, min, sec)
            false -> cancelNotification()
        }
    }



    /**
     * Creates a new alarm, notification and timer
     */
    private fun startTimer(hour:Int, min:Int, sec:Int) {
        _alarmOn.value?.let {
            if (!it) {
                _alarmOn.value = true
/*                val selectedInterval = when (timerLengthSelection) {
                    0 -> second * 10 //For testing only
                    else ->timerLengthOptions[timerLengthSelection] * minute
                }*/

                val triggerTime = SystemClock.elapsedRealtime() + (hour * hourTimer) + (min * minute) + (sec * second)

                // call cancel notification everytime timer is start.
                val notificationManager =
                    ContextCompat.getSystemService(
                        app,
                        NotificationManager::class.java
                    ) as NotificationManager
                notificationManager.cancelNotifications()

                AlarmManagerCompat.setExactAndAllowWhileIdle(
                    alarmManager,
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerTime,
                    notifyPendingIntent
                )

                viewModelScope.launch {
                    saveTime(triggerTime)
                }
            }
        }
        createTimer()
    }

    private suspend fun saveTime(triggerTime: Long) =
        withContext(Dispatchers.IO) {
            prefs.edit().putLong(TRIGGER_TIME, triggerTime).apply()
        }

     fun cancelNotification() {
        resetTimer()
        alarmManager.cancel(notifyPendingIntent)
    }



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

    fun onTaskSelected(userTask: UserTask) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigateToEditTodoScreen(userTask))
    }

    sealed class TasksEvent {
        data class NavigateToEditTodoScreen(val userTask: UserTask) : TasksEvent()
    }
}