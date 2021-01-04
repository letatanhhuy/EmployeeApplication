package com.example.employeeapplication

import android.app.Application
import com.example.employeeapplication.core.AppCoreComponent
import com.example.employeeapplication.core.CoreComponentProvider

class EmployeeApplication : Application(), CoreComponentProvider {
    private val coreComponent = AppCoreComponent(this)
    override fun getCoreComponent(): AppCoreComponent {
        return coreComponent
    }
}