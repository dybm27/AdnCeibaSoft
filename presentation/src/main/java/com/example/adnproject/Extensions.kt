package com.example.adnproject

import android.app.Activity
import android.widget.Toast

fun Activity.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}