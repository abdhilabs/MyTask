package com.abdhilabs.mytask.ui.activities

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.adapter.TaskAdapter
import com.abdhilabs.mytask.base.BaseActivity
import com.abdhilabs.mytask.databinding.ActivityMainBinding
import com.abdhilabs.mytask.di.injector
import com.abdhilabs.mytask.ui.fragment.TaskFragment
import com.abdhilabs.mytask.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : BaseActivity<ActivityMainBinding>() {

    val viewmodel: TaskViewModel by lazy {
        ViewModelProvider(this, injector.taskViewModelFactory())[TaskViewModel::class.java]
    }

    lateinit var bottomSheet: TaskFragment

    private lateinit var taskAdapter: TaskAdapter

    override fun resourceLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        binding.lifecycleOwner = this
        binding.activity = this
        binding.viewmodel = viewmodel
        taskAdapter = TaskAdapter()
        binding.rvTask.adapter = taskAdapter
        initSwipe()
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
                Snackbar.make(binding.root, "Successfully deleted task", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            viewmodel.saveTask(task)
                        }
                        show()
                    }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvTask)
        }
    }

    fun onAddButtonClicked() {
        bottomSheet = TaskFragment()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }
}
