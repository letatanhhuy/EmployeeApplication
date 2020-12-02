package com.example.employeeapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.employeeapplication.data.EmployeeRepo
import com.example.employeeapplication.network.EmployeeApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class EmployeeMainViewModelFactory(
    private val employeeRepo: EmployeeRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EmployeeMainViewModel(employeeRepo, ioDispatcher) as T
    }
}