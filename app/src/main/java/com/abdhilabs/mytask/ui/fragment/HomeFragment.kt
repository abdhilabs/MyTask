package com.abdhilabs.mytask.ui.fragment

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.abstraction.BaseFragment
import com.abdhilabs.mytask.adapter.TaskAdapter
import com.abdhilabs.mytask.databinding.FragmentHomeBinding
import com.abdhilabs.mytask.utils.cancelNotification
import com.abdhilabs.mytask.utils.setupNotification
import com.abdhilabs.mytask.utils.snackbar
import com.abdhilabs.mytask.viewmodel.TaskViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar

class HomeFragment : BaseFragment<FragmentHomeBinding, TaskViewModel>() {

    private lateinit var taskAdapter: TaskAdapter

    override fun layoutResourceId(): Int = R.layout.fragment_home

    override fun getViewModelClass(): Class<TaskViewModel> = TaskViewModel::class.java

    override fun initViewCreated() {
        taskAdapter = TaskAdapter()
        binding.fragment = this
        binding.viewmodel = vm
        initSwipe()
        with(binding) {
            rvTask.adapter = taskAdapter
//            turnNotification.setChecked(pref.isChecked)
            turnNotification.setOnCheckedChangeListener { isChecked ->
                if (isChecked) {
                    context?.setupNotification()
                } else {
                    context?.cancelNotification()
                }
            }
            vm.messageSuccess.observe(this@HomeFragment, { msg ->
                binding.root.snackbar(msg)
            })
        }
        taskAdapter.setOnItemClickListener { task ->
//            val dialog = DetailFragment(task)
//            dialog.show(childFragmentManager, dialog.tag)
        }
        observerData()
    }

    private fun observerData(){
        vm.task.observe(this, {
            if (it != null) {
                taskAdapter.differ.submitList(it)
                binding.rvTask.visibility = View.VISIBLE
            } else {
                binding.rvTask.visibility = View.GONE
            }
        })
    }

    private fun initSwipe() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = taskAdapter.differ.currentList[position]
//                viewmodel.deleteTask(task)
                binding.root.snackbar("Successfully deleted task").apply {
                    duration = BaseTransientBottomBar.LENGTH_LONG
                    setAction("Undo") {
//                        viewmodel.saveTask(task)
                        if (position < 1) taskAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvTask)
        }
    }

    fun onAddButtonClicked() {
//        val bottomSheet = AddTaskFragment(null)
//        bottomSheet.show(frag, bottomSheet.tag)
    }
}