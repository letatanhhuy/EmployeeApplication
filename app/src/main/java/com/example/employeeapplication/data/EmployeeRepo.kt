package com.example.employeeapplication.data

import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.network.EmployeeApi

class EmployeeRepo(private val employeeApi: EmployeeApi) {
    suspend fun getAllEmployee(): List<Employee> {
        return employeeApi.getAllEmployees().employeeList
    }
}