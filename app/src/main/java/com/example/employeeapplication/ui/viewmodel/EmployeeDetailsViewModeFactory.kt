package com.example.employeeapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.employeeapplication.repo.EmployeeRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class EmployeeDetailsViewModelFactory(
    private val employeeRepo: EmployeeRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EmployeeDetailsViewModel(employeeRepo, ioDispatcher) as T
    }
}