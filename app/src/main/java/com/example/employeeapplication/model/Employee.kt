package com.example.employeeapplication.model

import com.google.gson.annotations.SerializedName


data class Employee(
    @JsonRequired
    @SerializedName("uuid")
    val uuid: String,
    @JsonRequired
    @SerializedName("full_name")
    val name: String,
    @SerializedName("phone_number")
    val phone: String,
    @JsonRequired
    @SerializedName("email_address")
    val email: String,
    @SerializedName("biography")
    val biography: String,
    @SerializedName("photo_url_small")
    val photoSmall: String,
    @SerializedName("photo_url_large")
    val photoLarge: String,
    @JsonRequired
    @SerializedName("team")
    val team: String,
    @JsonRequired
    @SerializedName("employee_type")
    val type: TYPE
)

enum class TYPE(name: String) {
    FULL_TIME("FullTime"),
    PART_TIME("PartTime"),
    CONTRACTOR("Contractor")
}

class EmployeeComparatorByName : Comparator<Employee> {
    override fun compare(p0: Employee, p1: Employee): Int {
        return p0.name.compareTo(p1.name)
    }
}

