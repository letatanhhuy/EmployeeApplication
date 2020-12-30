package com.example.employeeapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.model.EmployeeComparatorByName
import com.example.employeeapplication.repo.EmployeeRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class EmployeeDetailsViewModel(
    private val employeeRepo: EmployeeRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    var employeeDetailsLiveData: MutableLiveData<Employee> = MutableLiveData()
        private set
    var loadingData: MutableLiveData<Boolean> = MutableLiveData(false)
        private set
    var showError: MutableLiveData<Boolean> = MutableLiveData(false)
        private set
    fun getEmployeeById(uuid: String) {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                Log.d(TAG, "get employee with id $uuid")
                loadingData.postValue(true)
                showError.postValue(false)
                employeeRepo.getEmployeeById(uuid)
            }.onSuccess { employee ->
                Log.d(TAG, "get employee list succeed")
                loadingData.postValue(false)
                employeeDetailsLiveData.postValue(employee)
            }.onFailure {
                Log.e(TAG, "get data failed with error message: ${it.message}")
                Log.e(TAG, "get data failed with error stack trace: ${it.printStackTrace()}")
                loadingData.postValue(false)
                showError.postValue(true)
            }
        }
    }
    companion object {
        private const val TAG = "EA:UI:DVM"
    }
}