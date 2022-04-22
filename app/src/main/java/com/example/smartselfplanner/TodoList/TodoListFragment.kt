package com.example.smartselfplanner.TodoList

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
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
import java.nio.file.Files.delete


class TodoListFragment : Fragment(), TodoListAdapter.OnItemClickListener {

    lateinit var binding: FragmentTodoListBinding
    private lateinit var viewModel: TodoListViewModel

    private val adapter = TodoListAdapter(this){show -> showDeleteMenu(show)}
    private var mainmenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

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

        binding.deleteButton.setOnClickListener {

        }


        //binding.deleteButton.isVisible = true
        return binding.root
    }

    override fun onMultipleSelect(userTask: UserTask) {
        binding.deleteButton.isVisible = true
    }

    override fun onEditClick(userTask: UserTask) {
        viewModel.onEditSelected(userTask)
    }

    override fun onDeleteClick(userTask: UserTask) {
        viewModel.onDeleteSelected(userTask)
    }

    fun showDeleteMenu(show: Boolean){
        mainmenu?.findItem(R.id.menu_delete)?.isVisible = show
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        mainmenu = menu
        inflater.inflate(R.menu.action_mode_menu, menu)
        showDeleteMenu(false)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when (item.itemId)
       {
           R.id.menu_delete -> {delete()}
       }
        return super.onOptionsItemSelected(item)
    }

    private fun delete(){
        val alertDialog = AlertDialog.Builder(this.context)
        alertDialog.setTitle("Delete")
        alertDialog.setMessage("Do you really want to delete these?")
        alertDialog.setPositiveButton("Delete"){_,_ ->
            adapter.deleteSelectedItem()
            showDeleteMenu(false)
        }
        alertDialog.setNegativeButton("Cancel"){_,_ ->

        }
        alertDialog.show()
    }


}