package com.example.domain

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun today(): Int {
    val formatter = SimpleDateFormat("E", Locale.getDefault())
    var day = 0
    when (formatter.format(getCurrentDateTime())) {
        "Mon" -> day = 1
        "Tue" -> day = 2
        "Wed" -> day = 3
        "Thu" -> day = 4
        "Fri" -> day = 5
        "Sat" -> day = 6
        "Sun" -> day = 7
    }
    return day
}



