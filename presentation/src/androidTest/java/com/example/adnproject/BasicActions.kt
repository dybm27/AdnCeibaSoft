package com.example.adnproject

import android.R
import android.view.View
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.adnproject.view.adapter.VehicleAdapter
import com.example.adnproject.view.viewholder.VehicleViewHolder
import org.hamcrest.Matcher


open class BasicActions {
    open fun onclick(idResource: Int) {
        onView(withId(idResource))
            .perform(click())
    }

    open fun editText(idResource: Int, text: String) {
        onView(withId(idResource))
            .perform(typeText(text), ViewActions.closeSoftKeyboard())
    }

    open fun validateText(idResource: Int, text: String) {
        onView(withId(idResource))
            .check(matches(withText(text)))
    }

    open fun validateTextToast(text: String) {
        onView(withText(text)).inRoot(ToastMatcher())
            .check(matches(withText(text)))
    }

    open fun onclickItemRecyclerView(
        idResourceRecyclerView: Int,
        position: Int,
        idResourceItem: Int
    ) {
        onView(withId(idResourceRecyclerView))
            .perform(
                RecyclerViewActions.scrollToPosition<VehicleViewHolder>(position),
                RecyclerViewActions.actionOnItemAtPosition<VehicleViewHolder>(
                    position,
                    customActionClick(idResourceItem)
                )
            )
    }

    private fun customActionClick(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Action Description"
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id) as View
                v.performClick()
            }
        }
    }
}