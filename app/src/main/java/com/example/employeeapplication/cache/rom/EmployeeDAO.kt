package com.example.employeeapplication.cache.rom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmployeeDAO {
    @Query("select * from employeeroomentity")
    fun getAllEmployee():List<EmployeeRoomEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEmployee(list:List<EmployeeRoomEntity>)
}