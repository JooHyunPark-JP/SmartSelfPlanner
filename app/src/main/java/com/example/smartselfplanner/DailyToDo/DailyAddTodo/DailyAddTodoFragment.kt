package com.example.smartselfplanner.DailyToDo.DailyAddTodo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.smartselfplanner.Database.UserTaskDatabase
import com.example.smartselfplanner.R
import com.example.smartselfplanner.TodoList.AddTodo.AddTodoViewModel
import com.example.smartselfplanner.TodoList.AddTodo.AddTodoViewModelFactory
import com.example.smartselfplanner.databinding.FragmentDailyAddTodoBinding
import com.example.smartselfplanner.databinding.FragmentDailyTodoBinding

class DailyAddTodoFragment : Fragment() {

    var sec: Int = 0
    var min: Int = 0
    var hour: Int = 0
    lateinit var binding: FragmentDailyAddTodoBinding
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
        binding.numPickerHour.maxValue = 23
        binding.numPickerMin.maxValue = 59
        binding.numPickerSec.maxValue = 59

        binding.numPickerHour.setOnValueChangedListener { numberPicker, i, i2 ->
            hour = numberPicker.value
        }
        binding.numPickerMin.setOnValueChangedListener { numberPicker, i, i2 ->
            min = numberPicker.value
        }
        binding.numPickerSec.setOnValueChangedListener { numberPicker, i, i2 ->
            sec = numberPicker.value
        }

        binding.setTimerCheckBox.setOnCheckedChangeListener { compoundButton, ischecked ->
            binding.dailyTaskTimer.isVisible = ischecked
        }

        //Database setup
        val application = requireNotNull(this.activity).application
        //val arguments = TodoListFragmentArgs.fromBundle(requireArguments())
        val dataSource = UserTaskDatabase.getInstance(application).UserTaskDatabaseDao
        val viewModelFactory = DailyAddTodoViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DailyAddTodoViewModel::class.java)


        binding.confirmButton.setOnClickListener {
            val todoString = binding.AddTodoText.text.toString()
            if (binding.setTimerCheckBox.isChecked) {
                if (todoString.equals("")) {
                    Toast.makeText(
                        context,
                        "Please write down your daily Task!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.addTodoWithTimer(todoString, hour, min, sec)
                    Toast.makeText(
                        context,
                        "Your Todo has been created! with timer!",
                        Toast.LENGTH_SHORT
                    ).show()
                    activity?.onBackPressed()
                }
            } else {
                if (todoString.equals("")) {
                    Toast.makeText(
                        context,
                        "Please write down your daily Task!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.addTodo(todoString)
                    Toast.makeText(context, "Your Todo has been created!", Toast.LENGTH_SHORT)
                        .show()
                    activity?.onBackPressed()
                }
            }
            //  Toast.makeText(context,"Your Todo has been created!", Toast.LENGTH_SHORT).show()


        }

        return binding.root
    }


}