package com.example.employeeapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.employeeapplication.model.Employee
import com.google.gson.Gson

class EmployeeDetailsViewModel(private val gson: Gson):ViewModel() {

    var employeesLiveData: MutableLiveData<Employee> = MutableLiveData()
        private set

    fun getEmployee(string: String) {
        runCatching {
            gson.fromJson(string, Employee::class.java)
        }.onSuccess {
            employeesLiveData.postValue(it)
        }.onFailure {
            Log.e(TAG, "getEmployee failed ${it.message}")
        }
    }

    companion object {
        private const val TAG = "EA:UI:EDV"
    }
}