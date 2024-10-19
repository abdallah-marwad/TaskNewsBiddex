package com.example.taskbiddex.common.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DateFormatter {
    fun formatDate(date: String): String {
        val parsedDate = ZonedDateTime.parse(date)
        val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return try {
            formattedDate
        }catch (e: Exception){
            date
        }
    }
}