package com.example.employeeapplication.cache.rom

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.model.TYPE

@Entity
class EmployeeRoomEntity (
    @PrimaryKey
    val uuid: String,
    val name: String,
    val phone: String,
    val email: String,
    val biography: String,
    val photoSmall: String,
    val photoLarge: String,
    val team: String,
    val type: TYPE)


fun List<EmployeeRoomEntity>.toDomainModel(): List<Employee> {
    return map {
        Employee(
            it.uuid,
            it.name,
            it.phone,
            it.email,
            it.biography,
            it.photoSmall,
            it.photoLarge,
            it.team,
            it.type)
    }
}

fun List<Employee>.toRoomDataModel(): List<EmployeeRoomEntity> {
    return map {
        EmployeeRoomEntity(
            it.uuid,
            it.name,
            it.phone,
            it.email,
            it.biography,
            it.photoSmall,
            it.photoLarge,
            it.team,
            it.type)
    }
}