package com.example.smartselfplanner.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserTask::class], version = 1, exportSchema = false)
abstract class UserTaskDatabase: RoomDatabase() {
    //Connects the database to the DAO.

    abstract val UserTaskDatabaseDao: UserTaskDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE : UserTaskDatabase? = null

        fun getInstance(context: Context): UserTaskDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserTaskDatabase::class.java,
                        "user_task_table"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}