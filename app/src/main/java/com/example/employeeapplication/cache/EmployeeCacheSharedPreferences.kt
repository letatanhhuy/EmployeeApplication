package com.example.employeeapplication.cache

import android.content.SharedPreferences
import android.util.Log
import com.example.employeeapplication.model.Employee
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EmployeeCacheSharedPreferences(private val sharedPreferences: SharedPreferences, private val gson: Gson):EmployeeCache {
    override fun getAllEmployee(): List<Employee> {
        val savedString = sharedPreferences.getString(EMPLOYEE_SAVE_TAG, "")
        var retVal = emptyList<Employee>()
        if (!savedString.isNullOrEmpty()) {
            runCatching {
                gson.fromJson<List<Employee>>(savedString, object : TypeToken<List<Employee>>() {}.type)
            }.onSuccess {
                retVal = it
            }.onFailure {
                Log.e(TAG, "getAllEmployee failed: ${it.message}")
                retVal = emptyList()
            }
        }
        return retVal
    }

    override fun saveAllEmployee(list: List<Employee>) {
        runCatching {
            val savedString = gson.toJson(list)
            sharedPreferences.edit()
                    .putString(EMPLOYEE_SAVE_TAG, savedString)
                    .apply()
        }.onSuccess {
            Log.d(TAG, "saveAllEmployee success")
        }.onFailure {
            Log.e(TAG, "saveAllEmployee failed ${it.message}")
        }

    }

    companion object {
        private const val EMPLOYEE_SAVE_TAG = "employee_save_tag"
        private const val TAG = "EA:CA:ESP"
    }
}