package com.example.employeeapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employeeapplication.R
import com.example.employeeapplication.cache.EmployeeDatabase
import com.example.employeeapplication.cache.rom.EmployeeRoomDatabase
import com.example.employeeapplication.core.getCoreComponent
import com.example.employeeapplication.repo.EmployeeRepo
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.ui.adapter.EmployeeViewAdapter
import com.example.employeeapplication.ui.common.switchView
import com.example.employeeapplication.ui.viewmodel.EmployeeMainViewModel
import com.example.employeeapplication.ui.viewmodel.EmployeeMainViewModelFactory
import kotlinx.android.synthetic.main.employee_list_view.view.*

class EmployeeMainViewFragment : Fragment() {
    @VisibleForTesting
    lateinit var employeeViewAdapter: EmployeeViewAdapter

    @VisibleForTesting
    var employeeList = emptyList<Employee>()

    @VisibleForTesting
    lateinit var employeeMainViewModel: EmployeeMainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        employeeMainViewModel = ViewModelProvider(
            this,
            EmployeeMainViewModelFactory(getCoreComponent().getApplicationEmployeeRepo())
        ).get(EmployeeMainViewModel::class.java)


        if (savedInstanceState == null) {
            employeeMainViewModel.getAllEmployee()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.employee_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        employeeViewAdapter = EmployeeViewAdapter {
            val listRowView = it as View
            val position: Int = view.recyclerViewEmployee.getChildAdapterPosition(listRowView)
            val employee = employeeList[position]
            Log.d(TAG, "clickListener: ${employee.uuid}")
            val bundle = Bundle()
            bundle.putString(EmployeeDetailViewFragment.RESTAURANT_ID_TAG, employee.uuid)
            val detailsFragment = EmployeeDetailViewFragment()
            detailsFragment.arguments = bundle
            switchView(R.id.mainFrame, detailsFragment)
        }

        view.recyclerViewEmployee.apply {
            val mainLayoutManager = LinearLayoutManager(this.context)
            layoutManager = mainLayoutManager
            adapter = employeeViewAdapter
            addItemDecoration(DividerItemDecoration(context, mainLayoutManager.orientation))
        }

        employeeMainViewModel.loadingData.observe(viewLifecycleOwner, {
            setLoadingEmployee(view, it)
        })

        employeeMainViewModel.showError.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(
                    this.context,
                    resources.getString(R.string.load_fail),
                    Toast.LENGTH_SHORT
                ).show()
                employeeMainViewModel.showError.value = false
            }
        })

        employeeMainViewModel.employeesLiveData.observe(this.viewLifecycleOwner, {
            employeeViewAdapter.updateEmployeeList(it)
            employeeList = it
            view.txtNothingToShow.visibility = if (employeeList.isEmpty()) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        })
    }

    private fun setLoadingEmployee(view: View, isLoading: Boolean) {
        view.progress_bar.visibility = if (isLoading) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    companion object {
        private const val TAG = "EA:UI:EMF"
    }
}