package com.example.smartselfplanner.UserMainPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartselfplanner.Database.UserTaskDatabaseDao

class UserMainPageViewModel (
    val database: UserTaskDatabaseDao,
    application: Application) : AndroidViewModel(Application()) {

    }

