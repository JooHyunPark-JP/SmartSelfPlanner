package com.example.smartselfplanner.DailyToDo

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.smartselfplanner.Database.UserTask
import com.example.smartselfplanner.R
import com.example.smartselfplanner.TodoList.TodoListAdapter
import org.w3c.dom.Text


class DailyTodoAdapter(
    private val listener: OnItemClickListener,
    private val showMenuDelete: (Boolean) -> Unit
) : RecyclerView.Adapter<DailyTodoAdapter.AddTodoListViewHolder>() {

    var data = mutableListOf<UserTask>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val itemSelectedList = mutableListOf<Int>()

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
        holder.timerSwitch.isVisible = false


        if (currentItem.dailyTimerHour.toString() != "null") {
            holder.timerTime.text =
                currentItem.dailyTimerHour.toString() + "h" + currentItem.dailyTimerMin.toString() + "m" + currentItem.dailyTimerSec.toString() + "s"
            holder.timerSwitch.isVisible = true
        } else {
            holder.timerTime.text = ""
        }

        if (itemSelectedList.isEmpty()) {
            holder.isCheckedCheckBox.isChecked = false
        }

        if (currentItem.TaskCompleted == true) {
            holder.itemView.setBackgroundColor(Color.GRAY)
            holder.itemView.isClickable = false
            holder.itemView.isFocusable = false
            holder.todoListMenu.setBackgroundColor(Color.GRAY)
            holder.todoListMenu.setImageResource(R.drawable.ic_check)
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
            holder.itemView.isClickable = true
            holder.itemView.isFocusable = true
            holder.todoListMenu.setBackgroundColor(Color.WHITE)
            holder.todoListMenu.setImageResource(R.drawable.ic_more)
        }

        holder.isCheckedCheckBox.setOnCheckedChangeListener { _, ischeck ->
            if (ischeck) {
                selectItem(currentItem, position)
                listener.oncheckboxClicked(currentItem)
            } else {
                if (itemSelectedList.contains(position)) {
                    currentItem.isChecked = false
                    listener.oncheckboxClicked(currentItem)
                    itemSelectedList.remove(position)
                    if (itemSelectedList.isEmpty()) {
                        showMenuDelete(false)
                    }
                }
            }
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
            } else {
                val builder = AlertDialog.Builder(it.context)
                builder
                    .setTitle("Do you want to un-do this task?")
                    .setPositiveButton(
                        "Yes",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            //currentItem.clearTask = "True"
                            currentItem.TaskCompleted = false
                            holder.itemView.isClickable = false
                            holder.itemView.isFocusable = false
                            holder.itemView.setBackgroundColor(Color.WHITE)
                            holder.todoListMenu.setBackgroundColor(Color.WHITE)
                            notifyItemChanged(position)
                            dialogInterface.dismiss()
                            listener.setTaskCompleted(currentItem)

                        })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                    })
                    .create()
                    .show()
            }
        }

        holder.timerSwitch.setOnCheckedChangeListener { _, ischecked ->
            if (ischecked) {
                listener.setAlarm(currentItem, holder.timerSwitch)
            } else {
                listener.setAlarmOff(currentItem, holder.timerSwitch)
            }
        }

        holder.todoListMenu.setOnClickListener {
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
        val todoListMenu: ImageButton = itemView.findViewById(R.id.todoMenu)
        val timerTime: TextView = itemView.findViewById(R.id.timerTime)
        val timerSwitch: Switch = itemView.findViewById(R.id.timerSwitch)
        val isCheckedCheckBox: CheckBox = itemView.findViewById(R.id.check_box_completed)
    }

    interface OnItemClickListener {
        fun onEditClick(userTask: UserTask)
        fun onDeleteClick(userTask: UserTask)
        fun setTaskCompleted(userTask: UserTask)
        fun setAlarm(userTask: UserTask, timer: Switch)
        fun setAlarmOff(userTask: UserTask, timer: Switch)
        fun onMultipleSelect()
        fun oncheckboxClicked(userTask: UserTask)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun deleteSelectedItem() {
        if (itemSelectedList.isNotEmpty()) {
            data.removeAll { item -> item.isChecked!! }
            itemSelectedList.clear()
            listener.onMultipleSelect()
        }
        notifyDataSetChanged()

    }

    private fun selectItem(currentItem: UserTask, position: Int) {
        itemSelectedList.add(position)
        currentItem.isChecked = true
        showMenuDelete(true)
    }

}