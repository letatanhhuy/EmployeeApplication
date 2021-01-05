package com.example.employeeapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson

class EmployeeDetailsViewModelFactory(private val gson: Gson) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EmployeeDetailsViewModel(gson) as T
    }
}
