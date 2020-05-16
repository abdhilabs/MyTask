package com.abdhilabs.mytask.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdhilabs.mytask.data.model.Task
import com.abdhilabs.mytask.databinding.ItemTaskBinding

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(task: Task) {
            binding.task = task
            binding.adapter = TaskAdapter()
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemTaskBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = differ.currentList[position]
        holder.bind(task)
        holder.itemView.setOnClickListener { onItemClickListener?.let { it(task) } }
    }

    fun moveItem(from: Int, to: Int) {
        val fromItem = differ.currentList.toMutableList()[from]
        differ.currentList.toMutableList().removeAt(from)
        if (to < from) {
            differ.currentList.toMutableList().add(to, fromItem)
        } else {
            differ.currentList.add(to - 1, fromItem)
        }
    }

    private var onItemClickListener: ((Task) -> Unit)? = null

    fun setOnItemClickListener(listener: (Task) -> Unit) {
        onItemClickListener = listener
    }
}