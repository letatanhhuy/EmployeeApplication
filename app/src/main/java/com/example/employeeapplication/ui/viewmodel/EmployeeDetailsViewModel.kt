package com.example.employeeapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.employeeapplication.core.getCoreComponent
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.ui.fragment.EmployeeMainViewFragment
import com.google.gson.Gson

class EmployeeDetailsViewModel(private val gson: Gson):ViewModel() {
    var employeesLiveData: MutableLiveData<Employee> = MutableLiveData()
        private set
    fun getEmployee(str:String) {
        val employee = gson.fromJson(str, Employee::class.java)
        employeesLiveData.postValue(employee)
    }
}