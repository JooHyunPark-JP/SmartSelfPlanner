package com.example.smartselfplanner.DailyToDo.DailyEditTodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
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

    var sec: Int = 0
    var min: Int = 0
    var hour: Int = 0

    lateinit var binding: FragmentDailyEditTodoBinding
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
        val arguments = DailyEditTodoFragmentArgs.fromBundle(requireArguments())
        val dataSource = UserTaskDatabase.getInstance(application).UserTaskDatabaseDao
        val viewModelFactory = DailyEditTodoViewModelFactory(arguments.dailyUserTask, dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DailyEditTodoViewModel::class.java)

        val tasks = viewModel.userDailyTask

        binding.editTodoText.setText(tasks)

        binding.confirmButton.setOnClickListener {
            val todoString = binding.editTodoText.text.toString()
            if (binding.setTimerCheckBox.isChecked) {
                if (todoString.equals("")) {
                    Toast.makeText(
                        context,
                        "Please write down your daily Task!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.editDailyTask(todoString, hour, min, sec)
                    Toast.makeText(
                        context,
                        "Your daily task has been updated with timer!",
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
                    viewModel.editDailyTaskWithoutTimer(todoString)
                    Toast.makeText(context, "Your daily task has been updated!", Toast.LENGTH_SHORT)
                        .show()
                    activity?.onBackPressed()
                }
            }

        }
        return binding.root
    }


}