package com.abdhilabs.mytask.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeFormatter {

    private val dateOutput = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("in", "ID"))
    val timeOutput = SimpleDateFormat("HH:mm:ss", Locale("in", "ID"))

    fun getCurrent(): String {
        val date = Date()
        return dateOutput.format(date)
    }

    fun getDate(date: Date): String {
        return dateOutput.format(date)
    }

    fun getDateFromString(dateString: String): String {
        val date = parseDate(dateString)
        return dateOutput.format(date)
    }

    fun getTimeFromString(dateString: String): String {
        val date = parseDate(dateString)
        return timeOutput.format(date)
    }

    fun parseDate(dateString: String): Date {
        val dateInput = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        return dateInput.parse(dateString)!!
    }

    fun getTimeTimestamp(time: String): Calendar {
        val splitTime = time.split(":")
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, splitTime[0].toInt())
        cal.set(Calendar.MINUTE, splitTime[1].toInt())
        cal.set(Calendar.SECOND, 0)
        return cal
    }
}