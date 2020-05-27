package com.abdhilabs.mytask.ui.activities

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.abdhilabs.mytask.App.Companion.pref
import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.adapter.TaskAdapter
import com.abdhilabs.mytask.base.BaseActivity
import com.abdhilabs.mytask.databinding.ActivityMainBinding
import com.abdhilabs.mytask.di.injector
import com.abdhilabs.mytask.ui.fragment.AddTaskFragment
import com.abdhilabs.mytask.ui.fragment.DetailFragment
import com.abdhilabs.mytask.utils.snackbar
import com.abdhilabs.mytask.viewmodel.TaskViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar

class MainActivity : BaseActivity<ActivityMainBinding>() {

    val viewmodel: TaskViewModel by lazy {
        ViewModelProvider(this, injector.taskViewModelFactory())[TaskViewModel::class.java]
    }

    private lateinit var taskAdapter: TaskAdapter

    override fun resourceLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        binding.lifecycleOwner = this
        binding.activity = this
        binding.viewmodel = viewmodel
        taskAdapter = TaskAdapter()
        initSwipe()
        with(binding) {
            rvTask.adapter = taskAdapter
            turnNotification.setChecked(pref.isChecked)
            turnNotification.setOnCheckedChangeListener { isChecked ->
                viewmodel!!.setAlarm(isChecked)
            }
            viewmodel!!.messageSuccess.observe(this@MainActivity, Observer { msg ->
                binding.root.snackbar(msg)
            })
        }
        taskAdapter.setOnItemClickListener { task ->
            val dialog = DetailFragment(task)
            dialog.show(supportFragmentManager, dialog.tag)
        }
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
                viewmodel.deleteTask(task)
                binding.root.snackbar("Successfully deleted task").apply {
                    duration = BaseTransientBottomBar.LENGTH_LONG
                    setAction("Undo") {
                        viewmodel.saveTask(task)
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
        val bottomSheet = AddTaskFragment(null)
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }
}
