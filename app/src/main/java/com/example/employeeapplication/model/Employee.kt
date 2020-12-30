package com.example.employeeapplication.model

import androidx.room.TypeConverter
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

enum class TYPE(name: String, val index: Int) {
    FULL_TIME("FullTime", 0),
    PART_TIME("PartTime", 1),
    CONTRACTOR("Contractor", 2)
}

class TypeConverters {
    @TypeConverter
    fun fromTypeToInt(value: TYPE): Int {
        return value.index
    }

    @TypeConverter
    fun fromIntToType(value: Int): TYPE {
        return when (value) {
            TYPE.FULL_TIME.index -> TYPE.FULL_TIME
            TYPE.PART_TIME.index -> TYPE.PART_TIME
            TYPE.CONTRACTOR.index -> TYPE.CONTRACTOR
            else -> TYPE.FULL_TIME
        }
    }
}

class EmployeeComparatorByName : Comparator<Employee> {
    override fun compare(p0: Employee, p1: Employee): Int {
        return p0.name.compareTo(p1.name)
    }
}

