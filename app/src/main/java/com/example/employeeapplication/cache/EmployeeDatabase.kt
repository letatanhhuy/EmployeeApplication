package com.example.employeeapplication.cache

import com.example.employeeapplication.model.Employee

interface EmployeeDatabase {
    fun getAllEmployee(): List<Employee>
    fun getEmployeeById(uuid: String): Employee?
    fun insertAllEmployee(list:List<Employee>)
}