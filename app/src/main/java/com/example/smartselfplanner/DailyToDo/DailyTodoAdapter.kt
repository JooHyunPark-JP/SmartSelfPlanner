package com.example.smartselfplanner.DailyToDo

import android.app.AlertDialog
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.smartselfplanner.Database.UserTask
import com.example.smartselfplanner.R
import com.example.smartselfplanner.TodoList.TodoListAdapter


class DailyTodoAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<DailyTodoAdapter.AddTodoListViewHolder>() {

    var data = listOf<UserTask>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTodoListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.todo_list_items,
            parent, false
        )
        return AddTodoListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AddTodoListViewHolder, position: Int) {
        val currentItem = data[position]
        holder.todoName.text = currentItem.Task

        if (currentItem.TaskCompleted == true){
            holder.itemView.setBackgroundColor(Color.GRAY)
            holder.itemView.isClickable = false
            holder.itemView.isFocusable = false
            holder.todoListEditbutton.setBackgroundColor(Color.GRAY)
        }
        else {
            holder.itemView.setBackgroundColor(Color.WHITE)
            holder.itemView.isClickable = true
            holder.itemView.isFocusable = true
            holder.todoListEditbutton.setBackgroundColor(Color.WHITE)
        }

            holder.itemView.setOnClickListener {
                if (currentItem.TaskCompleted == false) {
                    currentItem.TaskCompleted = true
                    Toast.makeText(
                        it.context,
                        "PrimaryKey: ${currentItem.TaskId}, TaskCompleted?: ${currentItem.TaskCompleted} ",
                        Toast.LENGTH_SHORT
                    ).show()
                    listener.setTaskCompleted(currentItem)
                }
            }

        holder.todoListEditbutton.setOnClickListener {
            val context = it.context
            val popupMenu = PopupMenu(context, holder.itemView, Gravity.END)
            popupMenu.inflate(R.menu.todo_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editText -> {
                        listener.onEditClick(currentItem)
                        true
                    }

                    R.id.deleteText -> {
                    AlertDialog.Builder(holder.itemView.context)
                        .setTitle("Delete")
                        .setIcon(R.drawable.ic_warning)
                        .setMessage("Are you sure want to delete?")
                        .setPositiveButton("Yes") { dialog, _ ->
                            listener.onDeleteClick(currentItem)
                            Toast.makeText(
                                context,
                                "Deleted!",
                                Toast.LENGTH_SHORT
                            ).show()
                            dialog.dismiss()
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                    true
                }
                    else -> true
                }
            }
            popupMenu.show()
            //val popup = androidx.appcompat.widget.PopupMenu::class.java.getDeclaredField("mPopup")
            val popup = android.widget.PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }
    }

    inner class AddTodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //  val wifiNameNumber: TextView = itemView.findViewById(R.id.wifiNameNumber)
        val todoName: TextView = itemView.findViewById(R.id.todoTask)
        val todoListEditbutton: ImageButton = itemView.findViewById(R.id.todoMenu)
    }

    interface OnItemClickListener {
        fun onEditClick(userTask: UserTask)
        fun onDeleteClick(userTask: UserTask)
        fun setTaskCompleted(userTask: UserTask)

    }

    override fun getItemCount(): Int {
        return data.size
    }


}