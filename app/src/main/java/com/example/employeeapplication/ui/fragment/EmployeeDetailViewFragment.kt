package com.example.employeeapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.employeeapplication.R
import com.example.employeeapplication.core.getCoreComponent
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.ui.viewmodel.EmployeeDetailsViewModel
import com.example.employeeapplication.ui.viewmodel.EmployeeDetailsViewModelFactory
import kotlinx.android.synthetic.main.employee_details_view.*
import kotlinx.android.synthetic.main.employee_details_view.view.*

class EmployeeDetailViewFragment:Fragment() {
    @VisibleForTesting
    lateinit var detailsViewModel: EmployeeDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsViewModel = ViewModelProvider(
            this,
            EmployeeDetailsViewModelFactory(getCoreComponent().getApplicationEmployeeRepo())
        ).get(EmployeeDetailsViewModel::class.java)
        val restaurantID = this.arguments?.getString(RESTAURANT_ID_TAG)
        if (restaurantID != null) {
            if (savedInstanceState == null) {
                detailsViewModel.getEmployeeById(restaurantID)
            }
        }
    }

    private fun setEmployeeDetails(view: View, employee: Employee) {
        txtEmployeeNameDetail.text = employee.name
        Glide.with(view)
            .load(employee.photoLarge)
            .error(Glide.with(view).load(R.drawable.ic_image_not_found_background))
            .into(view.imageEmployeeDetail)
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
        detailsViewModel.loadingData.observe(viewLifecycleOwner, {
            //setLoadingRestaurants(view, it)
        })

        detailsViewModel.showError.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(this.context, "Load failed", Toast.LENGTH_SHORT).show()
                detailsViewModel.showError.value = false
            }
        })

        detailsViewModel.employeeDetailsLiveData.observe(this.viewLifecycleOwner, {
            if (it != null) {
                setEmployeeDetails(view, it)
            }
        })
    }

    companion object {
        private const val TAG = "EA:UI:EDF"
        const val RESTAURANT_ID_TAG = "EA:RESTAURANT_ID_TAG"
    }
}