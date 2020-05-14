package com.abdhilabs.mytask.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(resourceLayoutId: Int): View =
    LayoutInflater.from(context).inflate(resourceLayoutId, this, false)