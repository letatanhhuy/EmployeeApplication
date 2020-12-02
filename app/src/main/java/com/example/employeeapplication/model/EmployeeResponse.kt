package com.example.employeeapplication.model

import com.google.gson.annotations.SerializedName

data class EmployeeResponse(
    @SerializedName("employees")
    val employeeList: List<Employee>
)