package com.example.employeeapplication.network

import com.example.employeeapplication.model.EmployeeResponse
import retrofit2.http.GET

interface EmployeeApi {
    @GET(employeeApiPath)
    suspend fun getAllEmployees(): EmployeeResponse

    companion object {
        private const val employeeApiPath = "employees.json"
        private const val employeeApiPathMalForm = "employees_malformed.json"
        private const val employeeApiPathEmpty = "employees_empty.json"
    }
}