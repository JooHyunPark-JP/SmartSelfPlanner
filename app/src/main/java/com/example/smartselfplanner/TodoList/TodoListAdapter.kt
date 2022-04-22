package com.example.smartselfplanner.TodoList

import android.app.AlertDialog
import android.graphics.Color
import android.view.*
import android.view.ActionMode.Callback
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.smartselfplanner.Database.UserTask
import com.example.smartselfplanner.R

class TodoListAdapter(private val listener: OnItemClickListener, private val showMenuDelete: (Boolean) -> Unit) : RecyclerView.Adapter<TodoListAdapter.todoListViewHolder>() {

/*    var data = listOf<UserTask>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }*/

    var data = mutableListOf<UserTask>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var isEnable = false
    private val itemSelectedList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): todoListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.todo_list_items,
            parent, false
        )
        return todoListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: todoListViewHolder, position: Int) {
        val currentItem = data[position]
        // holder.wifiNameNumber.text = position.toString()
        holder.todoName.text = currentItem.Task
        holder.isCheckedCheckBox.isChecked = false


        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, "PrimaryKey: ${currentItem.TaskId}, ischecked?: ${currentItem.isChecked} ", Toast.LENGTH_SHORT).show()
            if(itemSelectedList.contains(position)){
                itemSelectedList.removeAt(position)
                holder.isCheckedCheckBox.isChecked = true
                holder.itemView.setBackgroundColor(Color.GRAY)
                currentItem.isChecked = false
                if(itemSelectedList.isEmpty()){
                    showMenuDelete(false)
                    isEnable = false
                }
            }
            else if (isEnable){
                selectItem(holder,currentItem,position)
            }
        }

        holder.itemView.setOnLongClickListener {
            selectItem(holder,currentItem,position)
            true
        }

        holder.isCheckedCheckBox.setOnClickListener {
            currentItem.isChecked = currentItem.isChecked == false
            selectItem(holder,currentItem,position)
        true

           // Toast.makeText(it.context, "PrimaryKey: ${currentItem.TaskId}, ischecked?: ${currentItem.isChecked} ", Toast.LENGTH_SHORT).show()
        }



        holder.todoListEditbutton.setOnClickListener {
            val context = it.context
            val popupMenu = PopupMenu(context, holder.itemView, Gravity.END)
            popupMenu.inflate(R.menu.todo_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editText -> {
                        val v = LayoutInflater.from(holder.itemView.context)
                            .inflate(R.layout.edit_todo_dialog, null)
                        val newTodo = v.findViewById<EditText>(R.id.todoEditText)
                        AlertDialog.Builder(holder.itemView.context)
                            .setView(v)
                            .setPositiveButton("Change todo!") { dialog, _ ->
                                currentItem.Task = newTodo.text.toString()
                                notifyDataSetChanged()
                                dialog.dismiss()
                                Toast.makeText(
                                    context,
                                    "You have changed your ToDo!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                listener.onEditClick(currentItem)

                            }
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
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

    private fun selectItem(holder: TodoListAdapter.todoListViewHolder, currentItem: UserTask, position: Int) {
        isEnable = true
        itemSelectedList.add(position)
        currentItem.isChecked = true
        showMenuDelete(true)
    }


    inner class todoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //  val wifiNameNumber: TextView = itemView.findViewById(R.id.wifiNameNumber)
        val todoName: TextView = itemView.findViewById(R.id.todoTask)
        val todoListEditbutton: ImageButton = itemView.findViewById(R.id.todoMenu)
        val isCheckedCheckBox: CheckBox = itemView.findViewById(R.id.check_box_completed)
    }

    interface OnItemClickListener {
        fun onEditClick(userTask: UserTask)
        fun onDeleteClick(userTask: UserTask)
        fun onMultipleSelect(userTask: UserTask)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun deleteSelectedItem(){
        if(itemSelectedList.isNotEmpty()){
            data.removeAll{item -> item.isChecked!! }
            isEnable = false
            itemSelectedList.clear()
        }
        notifyDataSetChanged()
    }
}