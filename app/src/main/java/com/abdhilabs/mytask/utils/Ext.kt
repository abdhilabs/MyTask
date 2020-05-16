package com.abdhilabs.mytask.utils

import android.content.Context
import android.view.LayoutInflater

fun inflate(context: Context): LayoutInflater? =
    LayoutInflater.from(context)