package com.abdhilabs.mytask.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast

fun inflate(context: Context): LayoutInflater? =
    LayoutInflater.from(context)

fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()