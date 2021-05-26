package com.example.domain

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class UtilitiesTest {
    @Test
    fun validateToday_isCorrect() {
        //Arrange
        val formatter = SimpleDateFormat("E", Locale.getDefault())
        val expect = when (formatter.format(getCurrentDateTime())) {
            "Mon" -> 1
            "Tue" -> 2
            "Wed" -> 3
            "Thu" -> 4
            "Fri" -> 5
            "Sat" -> 6
            else -> 7
        }
        //Act
        val today = today()
        //Assert
        Assert.assertEquals(expect, today)
    }
}