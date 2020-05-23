package com.abdhilabs.mytask.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {

    private val sp: SharedPreferences by lazy {
        context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    private val spe: SharedPreferences.Editor by lazy {
        sp.edit()
    }

    fun clear() {
        sp.edit().clear().apply()
    }

    var isChecked: Boolean
        get() = sp.getBoolean(SP_ISCHECKED, false)
        set(value) = spe.putBoolean(SP_ISCHECKED, value).apply()

    var username: String?
        get() = sp.getString(SP_USERNAME, "")
        set(value) = spe.putString(SP_USERNAME, value).apply()
}