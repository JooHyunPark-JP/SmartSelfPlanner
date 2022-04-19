package com.example.smartselfplanner.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserTaskDatabaseDao {
    @Insert
     fun insert(task: UserTask)

    @Update
     fun update(task:UserTask)

    @Query("SELECT * FROM user_task_table WHERE TaskId = :key ")
    fun get(key: Long): UserTask

    @Query("DELETE From user_task_table")
    fun clear()

    @Query("DELETE From user_task_table where TaskId = :key")
    fun deleteRow(key: Long)

    @Query("SELECT * FROM user_task_table order by TaskId ASC")
    fun getAllTask(): LiveData<List<UserTask>>

    //Get the latest row (most recently)
    @Query("SELECT * FROM user_task_table ORDER BY TaskId DESC LIMIT 1")
    fun getMostRecentTask(): UserTask?

    @Query("SELECT * FROM user_task_table WHERE task_type = :key ORDER BY TaskId ASC  ")
    fun getTodoTask(key: String): LiveData<List<UserTask>>


/*    // Cant use offset without set a limit.
    //Getting all to do lists from this UUID.
    //The first row should be a just an UUID without TO DO value. Therefore it shouldn't be now shown at todoList.
    //Check this: https://stackoverflow.com/questions/3173635/offset-mysql-without-limit
    @Query("SELECT * FROM wifi_todo_table WHERE wifi_uuid_name = :key ORDER BY wifiAndTodoID ASC LIMIT 99999 OFFSET 1 ")
    fun getTodoListWithWifiUuid(key: String): LiveData<List<WifiAndTodo>>

    //Display only one Wifi UUID on the list.
    @Query("SELECT wifi_uuid_name, wifiAndTodoID FROM wifi_todo_table Group by wifi_uuid_name Having wifiAndTodoID = MIN(wifiAndTodoID) order by wifiAndTodoID ")
    fun getOnlyOneWifiForEach(): LiveData<List<WifiAndTodo>>

    @Query("SELECT EXISTS (SELECT * FROM wifi_todo_table WHERE wifi_uuid_name = :key)")
    fun checkExistedWifi(key: String): Boolean*/
}