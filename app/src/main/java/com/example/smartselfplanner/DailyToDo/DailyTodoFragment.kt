package com.example.smartselfplanner.DailyToDo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartselfplanner.Database.UserTask
import com.example.smartselfplanner.Database.UserTaskDatabase
import com.example.smartselfplanner.R
import com.example.smartselfplanner.TodoList.TodoListAdapter
import com.example.smartselfplanner.TodoList.TodoListFragmentDirections
import com.example.smartselfplanner.TodoList.TodoListViewModel
import com.example.smartselfplanner.TodoList.TodoListViewModelFactory
import com.example.smartselfplanner.UserMainPage.UserMainFragmentDirections
import com.example.smartselfplanner.databinding.FragmentDailyTodoBinding
import com.example.smartselfplanner.utils.setElapsedTime
import kotlinx.coroutines.flow.collect

class DailyTodoFragment : Fragment(), DailyTodoAdapter.OnItemClickListener {

    lateinit var binding : FragmentDailyTodoBinding
    private lateinit var viewModel: DailyTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDailyTodoBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        val datasource = UserTaskDatabase.getInstance(application).UserTaskDatabaseDao
        val viewModelFactory = DailyTodoViewModelFactory(datasource,application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DailyTodoViewModel::class.java)

        val adapter = DailyTodoAdapter(this)
        //bind recyclerview to TodoListAdapter
        binding.toDoRecyclerView.adapter = adapter
        binding.toDoRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.toDoRecyclerView.setHasFixedSize(true)

        binding.AddDailyTaskButton.setOnClickListener {
            this.findNavController().navigate(DailyTodoFragmentDirections.actionDailyTodoFragmentToDailyAddTodoFragment())
        }

        viewModel.dailyTodoList.observe(viewLifecycleOwner, Observer {
            adapter.data = it
        })

/*
        viewModel.isAlarmOn.observe(viewLifecycleOwner, Observer { alarmOn->
            binding.onOffSwitch.isChecked = alarmOn

            binding.onOffSwitch.setOnCheckedChangeListener { _, ischecked ->
                if(ischecked)
                {
                   // viewModel.setAlarm(true)

                    Log.d("switchChanged", "On ")
                }

                else
                {
                    //viewModel.setAlarm(false)
                    Log.d("switchChanged2", "Off ")
                }
            }
        })*/



        viewModel.elapsedTime.observe(viewLifecycleOwner, Observer { time ->
            binding.textView.setElapsedTime(time)
        })


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.tasksEvent.collect {event->
                when(event){
                    is DailyTodoViewModel.TasksEvent.NavigateToEditTodoScreen -> {
                        val action = DailyTodoFragmentDirections.actionDailyTodoFragmentToDailyEditTodoFragment(event.userTask)
                        Log.d("actiontesting", event.userTask.Task)
                        findNavController().navigate(action)
                    }
                }
            }
        }

        // call create channel
        createChannel(
            getString(R.string.dailyTask_channel_id),
            getString(R.string.dailyTask_channel_name)
        )

        return binding.root
    }

    override fun onDeleteClick(userTask: UserTask) {
        viewModel.onDeleteSelected(userTask)
    }

    override fun setTaskCompleted(userTask: UserTask) {
        viewModel.onSetTaskCompleted(userTask)
    }

    override fun onEditClick(userTask: UserTask) {
        viewModel.onTaskSelected(userTask)
    }

    override fun setAlarm(userTask: UserTask, timer:Switch) {
/*        viewModel.isAlarmOn.observe(viewLifecycleOwner, Observer { alarmOn ->
            timer.isChecked = alarmOn*/
            viewModel.cancelNotification()
            viewModel.setAlarm(true,userTask)
            Toast.makeText(context, "Switch ON!", Toast.LENGTH_SHORT).show()


    }

    override fun setAlarmOff(userTask: UserTask,timer:Switch) {
        viewModel.setAlarm(false,userTask)
    }

    //Create Channel for notification for dailytasks.
    private fun createChannel(channelId: String, channelName: String) {
        //START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                //change importance
                NotificationManager.IMPORTANCE_HIGH
            )// disable badges for this channel
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.daily_task_notification_channel_description)

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }
        // TODO: Step 1.6 END create a channel
    }



}