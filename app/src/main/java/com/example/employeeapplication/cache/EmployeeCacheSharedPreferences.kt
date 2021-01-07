package com.example.employeeapplication.cache

import android.content.SharedPreferences
import android.util.Log
import com.example.employeeapplication.model.Employee
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EmployeeCacheSharedPreferences(private val sharedPreferences: SharedPreferences
                                     , private val gson: Gson):EmployeeCache {
    override fun getAllEmployee(): List<Employee> {
        val savedString = sharedPreferences.getString(ALL_EMPLOYEE_TAG, "")
        Log.d(TAG, "getAllEmployee: $savedString")
        var retVal = emptyList<Employee>()
        if (!savedString.isNullOrEmpty()) {
            retVal = gson.fromJson(savedString, object : TypeToken<List<Employee>>(){}.type)
        }
        return retVal
    }

    override fun saveAllEmployee(list: List<Employee>) {
        val saveString = gson.toJson(list)
        Log.d(TAG, "saveAllEmployee: $saveString")
        sharedPreferences
                .edit()
                .putString(ALL_EMPLOYEE_TAG, saveString)
                .apply()
    }

    companion object {
        private const val ALL_EMPLOYEE_TAG = "all_employee_tag"
        private const val TAG = "EA:CA:ESP"
    }
}