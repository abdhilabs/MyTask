package com.abdhilabs.mytask.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.abdhilabs.mytask.R
import com.google.android.material.snackbar.Snackbar

fun Context.toast(msg: String): Toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT).apply {
    view?.setBackgroundResource(R.drawable.bg_rounded_toast)
    setText(msg)
    duration = Toast.LENGTH_SHORT
    show()
}

fun View.snackbar(msg: String?) = Snackbar.make(this, msg.toString(), Snackbar.LENGTH_SHORT).apply {
    view.setBackgroundColor(resources.getColor(R.color.redColor, null))
    show()
}