package com.example.employeeapplication.cache.sharedpreferrence

import android.content.SharedPreferences
import android.util.Log
import com.example.employeeapplication.cache.EmployeeDatabase
import com.example.employeeapplication.model.Employee
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class EmployeeSharedPreferenceDatabase(private val sharedPreferences:SharedPreferences
                                       , private val gson: Gson):EmployeeDatabase {
    override fun getEmployeeById(uuid: String): Employee? {
        val list = getAllEmployee()
        list.forEach {
            if (it.uuid == uuid) {
                return it
            }
        }
        throw Exception("Cannot find this UUID: $uuid")
    }
    override fun getAllEmployee(): List<Employee> {
        val savedString = sharedPreferences.getString(EMPLOYEE_LIST_TAG,"")
        var retval = emptyList<Employee>()
        if (!savedString.isNullOrEmpty()) {
            retval = gson.fromJson(savedString, object : TypeToken<List<Employee>>() {}.type)
        }
        return retval
    }

    override fun insertAllEmployee(list: List<Employee>) {
        val saveString = gson.toJson(list)
        if (!saveString.isNullOrEmpty()) {
            sharedPreferences.edit()
                .putString(EMPLOYEE_LIST_TAG, saveString)
                .apply()
        }
    }

    companion object {
        private const val TAG = "EA:CA:ESP"
        private const val EMPLOYEE_LIST_TAG = "employees_list"
    }
}