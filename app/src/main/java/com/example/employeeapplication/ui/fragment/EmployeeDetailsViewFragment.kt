package com.example.employeeapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.employeeapplication.R
import com.example.employeeapplication.core.getCoreComponent
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.ui.viewmodel.EmployeeDetailsViewModel
import com.example.employeeapplication.ui.viewmodel.EmployeeDetailsViewModelFactory
import kotlinx.android.synthetic.main.employee_details_view.*

class EmployeeDetailsViewFragment :Fragment(){
    @VisibleForTesting
    lateinit var employee:Employee

    @VisibleForTesting
    lateinit var employeeDetailsViewModel: EmployeeDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        employeeDetailsViewModel = ViewModelProvider(
            this,
            EmployeeDetailsViewModelFactory(getCoreComponent().getApplicationGSON())
        ).get(EmployeeDetailsViewModel::class.java)

        if (savedInstanceState == null) {
            val data = arguments?.getString(EmployeeMainViewFragment.EMPLOYEE_DETAILS_TAG)?:""
            employeeDetailsViewModel.getEmployee(data)
        }
    }
    private fun updateEmployeeDetailsView(employee: Employee) {
        Glide.with(this)
            .load(employee.photoLarge)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) //cache - default also cache so we may don't need to set this
            .error(Glide.with(this).load(R.drawable.ic_image_not_found_background))
            .into(imageEmployeeDetails)
        txtEmployeeNameDetail.text = employee.name
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.employee_details_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        employeeDetailsViewModel.employeesLiveData.observe(viewLifecycleOwner, {
            updateEmployeeDetailsView(it)
        })
    }
}