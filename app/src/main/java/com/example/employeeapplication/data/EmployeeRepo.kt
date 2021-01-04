package com.example.employeeapplication.data

import android.util.Log
import com.example.employeeapplication.cache.EmployeeCache
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.network.EmployeeApi

class EmployeeRepo(private val employeeApi: EmployeeApi, private val employeeCache: EmployeeCache) {
    suspend fun getAllEmployee(): List<Employee> {
        runCatching {
            employeeApi.getAllEmployees().employeeList
        }.onSuccess {
            employeeCache.saveAllEmployee(it)
            return it
        }.onFailure {
            Log.e(TAG, "getAllEmployee failed: ${it.message}")
        }
        return employeeCache.getAllEmployee()
    }

    companion object {
        private const val TAG = "EA:CA:ERP"
    }
}