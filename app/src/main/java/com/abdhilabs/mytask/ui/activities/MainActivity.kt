package com.abdhilabs.mytask.ui.activities

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.adapter.TaskAdapter
import com.abdhilabs.mytask.base.BaseActivity
import com.abdhilabs.mytask.data.repository.TaskRepository
import com.abdhilabs.mytask.databinding.ActivityMainBinding
import com.abdhilabs.mytask.db.AppDatabase
import com.abdhilabs.mytask.ui.fragment.TaskFragment
import com.abdhilabs.mytask.viewmodel.TaskViewModel
import com.abdhilabs.mytask.viewmodel.TaskViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : BaseActivity<ActivityMainBinding>() {

    lateinit var viewModel: TaskViewModel
    lateinit var bottomSheet: TaskFragment

    private lateinit var taskAdapter: TaskAdapter

    override fun resourceLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        binding.lifecycleOwner = this
        binding.activity = this
        initViewmodel()
        setupRv()
        initSwipe()
        viewModel.getTask().observe(this, Observer {
            taskAdapter.differ.submitList(it)
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
                viewModel.deleteTask(task)
                Snackbar.make(binding.root, "Successfully deleted task", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            viewModel.saveTask(task)
                        }
                        show()
                    }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvTask)
        }
    }

    private fun initViewmodel() {
        val repo = TaskRepository(AppDatabase(this))
        val viewModelFactory = TaskViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]
    }

    private fun setupRv() {
        taskAdapter = TaskAdapter()
        binding.rvTask.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    fun onAddButtonClicked() {
        bottomSheet = TaskFragment()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }
}
