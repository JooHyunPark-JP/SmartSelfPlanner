package com.example.smartselfplanner.UserMainPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.smartselfplanner.Database.UserTaskDatabase
import com.example.smartselfplanner.R
import com.example.smartselfplanner.databinding.FragmentUserMainBinding


class UserMainFragment : Fragment() {

    lateinit var binding: FragmentUserMainBinding
    private lateinit var viewModel: UserMainPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserMainBinding.inflate(layoutInflater)


        //setting up getting data from the database
        val application = requireNotNull(this.activity).application
        val datasource = UserTaskDatabase.getInstance(application).UserTaskDatabaseDao
        val viewModelFactory = UserMainPageViewModelFactory(datasource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserMainPageViewModel::class.java)


        binding.toDoTaskView.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_userMainFragment_to_todoListFragment)
        }

        binding.dailyTaskView.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_userMainFragment_to_dailyTodoFragment)
        }

        viewModel.todoCount.observe(viewLifecycleOwner, Observer { count ->
            binding.toDoCount.text = "Total To-do: " + count.toString()
        })


        viewModel.dailyTodoCount.observe(viewLifecycleOwner, Observer { count ->
            binding.dailyTotalTodoText.text = "Total Daily tasks: " + count.toString()
        })

        viewModel.getDailyLeftTodoCount.observe(viewLifecycleOwner, Observer { count ->
            binding.dailyLeftTodoText.text = count.toString() + " tasks left!"
        })


/*        binding.dabaseDeleteButton.setOnClickListener {
            viewModel.onClear()
        }*/
        return binding.root
    }


}