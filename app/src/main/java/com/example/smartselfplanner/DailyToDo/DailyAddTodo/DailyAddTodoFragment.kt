package com.example.smartselfplanner.DailyToDo.DailyAddTodo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.smartselfplanner.Database.UserTaskDatabase
import com.example.smartselfplanner.R
import com.example.smartselfplanner.TodoList.AddTodo.AddTodoViewModel
import com.example.smartselfplanner.TodoList.AddTodo.AddTodoViewModelFactory
import com.example.smartselfplanner.databinding.FragmentDailyAddTodoBinding
import com.example.smartselfplanner.databinding.FragmentDailyTodoBinding

class DailyAddTodoFragment : Fragment() {

    lateinit var binding : FragmentDailyAddTodoBinding
    private lateinit var viewModel: DailyAddTodoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDailyAddTodoBinding.inflate(inflater)

        //Database setup
        val application = requireNotNull(this.activity).application
        //val arguments = TodoListFragmentArgs.fromBundle(requireArguments())
        val dataSource = UserTaskDatabase.getInstance(application).UserTaskDatabaseDao
        val viewModelFactory = DailyAddTodoViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DailyAddTodoViewModel::class.java)


        binding.confirmButton.setOnClickListener {
            val todoString = binding.AddTodoText.text.toString()
            viewModel.addTodo(todoString)
            Toast.makeText(context,"Your Todo has been created!", Toast.LENGTH_SHORT).show()
            activity?.onBackPressed()
        }



        return binding.root
    }


}