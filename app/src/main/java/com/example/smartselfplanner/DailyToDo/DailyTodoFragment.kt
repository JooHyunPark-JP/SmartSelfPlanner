package com.example.smartselfplanner.DailyToDo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartselfplanner.Database.UserTask
import com.example.smartselfplanner.Database.UserTaskDatabase
import com.example.smartselfplanner.R
import com.example.smartselfplanner.TodoList.TodoListAdapter
import com.example.smartselfplanner.TodoList.TodoListFragmentDirections
import com.example.smartselfplanner.TodoList.TodoListViewModel
import com.example.smartselfplanner.TodoList.TodoListViewModelFactory
import com.example.smartselfplanner.databinding.FragmentDailyTodoBinding

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
        val viewModelFactory = DailyTodoViewModelFactory(datasource)
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
        return binding.root
    }

    override fun onDeleteClick(userTask: UserTask) {

        viewModel.onDeleteSelected(userTask)
    }

    override fun setTaskCompleted(userTask: UserTask) {
        viewModel.onSetTaskCompleted(userTask)
    }

}