package com.example.smartselfplanner.TodoList

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
import com.example.smartselfplanner.UserMainPage.UserMainPageViewModel
import com.example.smartselfplanner.UserMainPage.UserMainPageViewModelFactory
import com.example.smartselfplanner.databinding.FragmentTodoListBinding


class TodoListFragment : Fragment(), TodoListAdapter.OnItemClickListener {

    lateinit var binding: FragmentTodoListBinding
    private lateinit var viewModel: TodoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTodoListBinding.inflate(layoutInflater)

        val application = requireNotNull(this.activity).application
        val datasource = UserTaskDatabase.getInstance(application).UserTaskDatabaseDao
        val viewModelFactory = TodoListViewModelFactory(datasource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TodoListViewModel::class.java)

        val adapter = TodoListAdapter(this)
        //bind recyclerview to TodoListAdapter
        binding.toDoRecyclerView.adapter = adapter
        binding.toDoRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.toDoRecyclerView.setHasFixedSize(true)

        //Give database's data to adapter to access the data.
        viewModel.todoList.observe(viewLifecycleOwner, Observer {
            adapter.data = it
        })

        binding.AddTodoButton.setOnClickListener {
            this.findNavController().navigate(TodoListFragmentDirections.actionTodoListFragmentToAddTodoFragment())
        }


        return binding.root
    }

    override fun onEditClick(userTask: UserTask) {
        TODO("Not yet implemented")
    }

    override fun onDeleteClick(userTask: UserTask) {
        TODO("Not yet implemented")
    }


}