package com.example.employeeapplication.data

import com.example.employeeapplication.cache.EmployeeCache
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.network.EmployeeApi

class EmployeeRepo(private val employeeApi: EmployeeApi, private val employeeCache: EmployeeCache) {
    suspend fun getAllEmployee(): List<Employee> {
        var retval = emptyList<Employee>()
        runCatching {
            employeeApi.getAllEmployees().employeeList
        }.onSuccess {
            employeeCache.saveAllEmployee(it)
            return it
        }.onFailure {
            retval = employeeCache.getAllEmployee()
        }
        return retval
    }
}


//UI/Fragment --> VM ---> Repo ---> Api
//                             ---> Caching (SP)