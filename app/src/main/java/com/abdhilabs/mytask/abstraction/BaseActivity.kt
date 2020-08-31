package com.abdhilabs.mytask.abstraction

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.abdhilabs.mytask.R

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: T

    @LayoutRes
    protected abstract fun resourceLayoutId(): Int

    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.statusBarColor = getColor(R.color.lightWhiteColor)
        binding = DataBindingUtil.setContentView(this, resourceLayoutId())

        initView()
    }

    protected fun hideKeyboard() {
        currentFocus.let {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(it?.windowToken, 0)
        }
    }
}