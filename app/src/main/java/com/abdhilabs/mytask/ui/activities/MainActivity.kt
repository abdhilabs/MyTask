package com.abdhilabs.mytask.ui.activities

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.abstraction.BaseActivity
import com.abdhilabs.mytask.adapter.TaskAdapter
import com.abdhilabs.mytask.databinding.ActivityMainBinding
import com.abdhilabs.mytask.ui.fragment.AddTaskFragment
import com.abdhilabs.mytask.ui.fragment.DetailFragment
import com.abdhilabs.mytask.utils.cancelNotification
import com.abdhilabs.mytask.utils.setupNotification
import com.abdhilabs.mytask.utils.snackbar
import com.google.android.material.snackbar.BaseTransientBottomBar
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>(), HasAndroidInjector {

    private lateinit var taskAdapter: TaskAdapter

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun resourceLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        AndroidInjection.inject(this)
        binding.lifecycleOwner = this
        binding.activity = this
//        binding.viewmodel = viewmodel
        taskAdapter = TaskAdapter()
        initSwipe()
        with(binding) {
            rvTask.adapter = taskAdapter
//            turnNotification.setChecked(pref.isChecked)
            turnNotification.setOnCheckedChangeListener { isChecked ->
                if (isChecked) {
                    this@MainActivity.setupNotification()
                } else {
                    this@MainActivity.cancelNotification()
                }
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
        val bottomSheet = AddTaskFragment(null)
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }
}
