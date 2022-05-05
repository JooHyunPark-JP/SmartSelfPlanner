package com.example.smartselfplanner.DailyToDo.DailyEditTodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.smartselfplanner.DailyToDo.DailyAddTodo.DailyAddTodoViewModel
import com.example.smartselfplanner.DailyToDo.DailyAddTodo.DailyAddTodoViewModelFactory
import com.example.smartselfplanner.Database.UserTaskDatabase
import com.example.smartselfplanner.R
import com.example.smartselfplanner.databinding.FragmentDailyAddTodoBinding
import com.example.smartselfplanner.databinding.FragmentDailyEditTodoBinding
import com.example.smartselfplanner.databinding.FragmentUserMainBinding


class DailyEditTodoFragment : Fragment() {

    lateinit var binding : FragmentDailyEditTodoBinding
    private lateinit var viewModel: DailyEditTodoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDailyEditTodoBinding.inflate(layoutInflater)

        //Database setup
        val application = requireNotNull(this.activity).application
        val arguments = DailyEditTodoFragmentArgs.fromBundle(requireArguments())
        val dataSource = UserTaskDatabase.getInstance(application).UserTaskDatabaseDao
        val viewModelFactory = DailyEditTodoViewModelFactory(arguments.dailyUserTask,dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DailyEditTodoViewModel::class.java)

        val tasks = viewModel.testing

        binding.EditTodoText.setText(tasks)
        return binding.root
    }

}