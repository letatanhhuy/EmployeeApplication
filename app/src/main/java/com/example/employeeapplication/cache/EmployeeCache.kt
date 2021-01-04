package com.example.employeeapplication.cache

import com.example.employeeapplication.model.Employee

interface EmployeeCache {
    fun getAllEmployee():List<Employee>
    fun saveAllEmployee(list: List<Employee>)
}