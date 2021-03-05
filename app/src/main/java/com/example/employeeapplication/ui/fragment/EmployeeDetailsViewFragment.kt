package com.example.employeeapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.employeeapplication.R
import com.example.employeeapplication.core.getCoreComponent
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.ui.viewmodel.EmployeeDetailsViewModel
import com.example.employeeapplication.ui.viewmodel.EmployeeDetailsViewModelFactory
import com.example.employeeapplication.ui.viewmodel.EmployeeMainViewModel
import com.example.employeeapplication.ui.viewmodel.EmployeeMainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.employee_detalis_view.*
import kotlinx.android.synthetic.main.employee_row_view.view.*

class EmployeeDetailsViewFragment:Fragment() {

    lateinit var employeeDetailsViewModel: EmployeeDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        employeeDetailsViewModel = ViewModelProvider(
            this,
            EmployeeDetailsViewModelFactory(getCoreComponent().getApplicationGSON())
        ).get(EmployeeDetailsViewModel::class.java)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val employeeStr = arguments?.getString(EmployeeMainViewFragment.TAG_CLICK_EMPLOYEE)
            if (!employeeStr.isNullOrEmpty()) {
                employeeDetailsViewModel.getEmployee(employeeStr)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        employeeDetailsViewModel.employeesLiveData.observe(viewLifecycleOwner, {
            txtEmployeeNameDetails.text = it.name
            Glide.with(view)
                .load(it.photoLarge)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(Glide.with(view).load(R.drawable.ic_image_not_found_background))
                .into(imageEmployeeDetails)
            }
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.employee_detalis_view, container, false)
    }

    companion object {
        private const val TAG = "EA:UI:EDF"
    }
}