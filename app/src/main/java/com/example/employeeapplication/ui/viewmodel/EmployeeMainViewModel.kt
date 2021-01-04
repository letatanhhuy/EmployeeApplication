package com.example.employeeapplication.ui.viewmodel

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeeapplication.data.EmployeeRepo
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.model.EmployeeComparatorByName
import com.google.gson.JsonParseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class EmployeeMainViewModel(
    private val employeeRepo: EmployeeRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    var employeesLiveData: MutableLiveData<List<Employee>> = MutableLiveData()
        private set
    var loadingData: MutableLiveData<Boolean> = MutableLiveData(false)
        private set
    var showError: MutableLiveData<Boolean> = MutableLiveData(false)
        private set

    fun getAllEmployee() {
        getAllEmployee(employeesLiveData, loadingData, showError)
    }

    @VisibleForTesting
    fun getAllEmployee(
        _employeesLiveData: MutableLiveData<List<Employee>>,
        _loadingData: MutableLiveData<Boolean>,
        _showError: MutableLiveData<Boolean>
    ) {
        Log.d(TAG, "get employee list start")
        if (_loadingData.value == true) {
            Log.d(TAG, "get employee list abort since already running")
            return
        }
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                Log.d(TAG, "get employee list ${Thread.currentThread()}")
                _loadingData.postValue(true)
                _showError.postValue(false)
                employeeRepo.getAllEmployee()
            }.onSuccess { employeeList ->
                Log.d(TAG, "get employee list result")
                employeeList.forEach {
                    Log.d(TAG, "$it")
                }
                _loadingData.postValue(false)
                Collections.sort(employeeList, EmployeeComparatorByName())
                _employeesLiveData.postValue(employeeList)
            }.onFailure {
                Log.e(TAG, "get data failed with error: ${it.message}")
                _loadingData.postValue(false)
                _showError.postValue(true)
                _employeesLiveData.postValue(emptyList())
            }
        }
    }

    companion object {
        private const val TAG = "EA:UI:EVM"
    }
}