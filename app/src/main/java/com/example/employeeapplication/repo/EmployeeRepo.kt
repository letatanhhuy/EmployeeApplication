package com.example.employeeapplication.repo

import android.util.Log
import com.example.employeeapplication.cache.EmployeeDatabase
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.network.EmployeeApi
import java.util.*

class EmployeeRepo(private val employeeApi: EmployeeApi, private val employeeDatabase: EmployeeDatabase) {

    suspend fun getEmployeeById(uuid: String):Employee? {
        runCatching {
            employeeDatabase.getEmployeeById(uuid)
        }.onSuccess {
            return it
        }.onFailure {
            Log.e(TAG, "load data error for employee id $uuid: ${it.message}")
        }
        return null
    }

    suspend fun getAllEmployee(): List<Employee> {
        runCatching {
            employeeApi.getAllEmployees().employeeList
        }.onSuccess {
            employeeDatabase.insertAllEmployee(it)
        }.onFailure {
            Log.e(TAG, "load data error: ${it.message}")
        }
        return employeeDatabase.getAllEmployee()
    }

    companion object {
        private const val TAG = "EA:RE:EMR"
    }
}