package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onTaskDeleted: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.taskName)
        val taskCheckbox: CheckBox = itemView.findViewById(R.id.taskCheckbox)
        val deleteIcon: ImageView = itemView.findViewById(R.id.deleteIcon) // Add delete icon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.name
        holder.taskCheckbox.isChecked = task.isCompleted
        holder.deleteIcon.visibility = if (task.isCompleted) View.VISIBLE else View.GONE

        // Handle checkbox toggle
        holder.taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
            holder.deleteIcon.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        // Handle delete button click
        holder.deleteIcon.setOnClickListener {
            showDeleteConfirmation(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    private fun showDeleteConfirmation(holder: TaskViewHolder, position: Int) {
        val context = holder.itemView.context
        AlertDialog.Builder(context)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Delete") { _, _ ->
                onTaskDeleted(position)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
