package com.example.smartselfplanner.AddTodo

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
import com.example.smartselfplanner.databinding.FragmentAddTodoBinding

// TODO: Rename parameter arguments, choose names that match

class AddTodoFragment : Fragment() {

    lateinit var binding: FragmentAddTodoBinding
    private lateinit var viewModel: AddTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTodoBinding.inflate(layoutInflater)


        val application = requireNotNull(this.activity).application
        //val arguments = TodoListFragmentArgs.fromBundle(requireArguments())
        val dataSource = UserTaskDatabase.getInstance(application).UserTaskDatabaseDao
        val viewModelFactory = AddTodoViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddTodoViewModel::class.java)
        //Log.d("UUIDTEST", viewModel.uuid)


        binding.confirmButton.setOnClickListener {
            val todoString = binding.AddTodoText.text.toString()
            Log.d("Edit text testing", todoString)
            viewModel.addTodo(todoString)
            Toast.makeText(context,"Your Todo has been created!", Toast.LENGTH_SHORT).show()
            activity?.onBackPressed()
        }
        return binding.root
    }

}