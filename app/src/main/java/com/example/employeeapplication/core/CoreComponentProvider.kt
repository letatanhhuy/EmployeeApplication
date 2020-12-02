package com.example.employeeapplication.core

import android.app.Activity
import androidx.fragment.app.Fragment

interface CoreComponentProvider {
    fun getCoreComponent(): AppCoreComponent
}

fun Activity.getCoreComponent(): AppCoreComponent =
    (application as CoreComponentProvider).getCoreComponent()

fun Fragment.getCoreComponent(): AppCoreComponent = activity?.getCoreComponent()
    ?: throw IllegalStateException("There is no current activity attached to this fragment to lookup for a CoreComponentProvider")