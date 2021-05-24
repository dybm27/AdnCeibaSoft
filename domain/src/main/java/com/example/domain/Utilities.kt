package com.example.domain

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun today(): Int {
    val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1
    return if (day == 0) {
        7
    } else {
        day
    }
}

fun createDate(date: String): Date {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return simpleDateFormat.parse(date)!!
}


