package com.kodego.mytodoapp2

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kodego.mytodoapp2.databinding.RowItemBinding

class TodoAdapter (var toDo: MutableList<ToDo>): RecyclerView.Adapter<TodoAdapter.ToDoViewHolder>() {
    var onTaskDelete : ((ToDo, Int) -> Unit) ? = null

    inner class ToDoViewHolder(var binding: RowItemBinding): RecyclerView.ViewHolder(binding.root)

    private fun toggleStrikeThrough(tvTask: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTask.paintFlags = tvTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvTask.paintFlags = tvTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowItemBinding.inflate(layoutInflater,parent,false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.binding.apply {
            tvTask.text = toDo[position].task
            btnDelete.setOnClickListener {
                onTaskDelete?.invoke(toDo[position],position)
            }
            cbDone.isChecked = toDo[position].isChecked
            toggleStrikeThrough(tvTask, toDo[position].isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tvTask, isChecked)
                toDo[position].isChecked =! toDo[position].isChecked
            }

        }
    }

    override fun getItemCount(): Int {
        return toDo.size
    }
}