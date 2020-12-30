package com.example.employeeapplication.cache.rom

import androidx.room.*
import com.example.employeeapplication.cache.EmployeeDatabase
import com.example.employeeapplication.model.Employee
import java.util.*


@Database(entities = [EmployeeRoomEntity::class], version = 1)
@TypeConverters(com.example.employeeapplication.model.TypeConverters::class)
abstract class EmployeeRoomDatabase: EmployeeDatabase, RoomDatabase() {
    abstract val employeeDAO:EmployeeDAO

    override fun getAllEmployee(): List<Employee> {
        return employeeDAO.getAllEmployee().toDomainModel()
    }

    override fun insertAllEmployee(list:List<Employee>) {
        employeeDAO.insertAllEmployee(list.toRoomDataModel())
    }

    override fun getEmployeeById(uuid: String): Employee? {
        TODO()
    }

    companion object {
        private const val DATABASE_NAME = "employees"
    }
}