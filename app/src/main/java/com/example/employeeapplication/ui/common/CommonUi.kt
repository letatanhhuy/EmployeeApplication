package com.example.employeeapplication.ui.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.employeeapplication.MainActivity

fun AppCompatActivity.switchView(viewId: Int, fragment: Fragment, addToBackStack: Boolean = false) {
    supportFragmentManager.beginTransaction().apply {
        replace(viewId, fragment, fragment.toString())
        if (addToBackStack) {
            addToBackStack(null)
        }
        commit()
    }
}

fun Fragment.switchView(id: Int, fragment: Fragment) {
    if (this.context is MainActivity) {
        val mainActivity = this.context as MainActivity
        mainActivity.switchView(id, fragment, true)
    }
}
