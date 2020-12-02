package com.example.employeeapplication.ui.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.switchView(viewId: Int, fragment: Fragment, addToBackStack: Boolean = false) {
    supportFragmentManager.beginTransaction().apply {
        replace(viewId, fragment, fragment.toString())
        if (addToBackStack) {
            addToBackStack(null)
            commit()
        }
        commitNow()
    }
}