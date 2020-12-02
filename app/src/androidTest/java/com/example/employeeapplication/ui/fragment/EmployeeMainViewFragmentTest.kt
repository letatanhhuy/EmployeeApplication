package com.example.employeeapplication.ui.fragment

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.fragment.app.testing.launchFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.employeeapplication.R
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.model.EmployeeResponse
import com.example.employeeapplication.model.TYPE
import com.google.common.truth.Truth
import kotlinx.android.synthetic.main.employee_list_view.view.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmployeeMainViewFragmentTest {
    private lateinit var appContext: Context
    private val uuid = "0d8fcc12-4d0c-425c-8355-390b312b909c"
    private val name = "Justine Mason"
    private val phone = "5553280123"
    private val email = "jmason.demo@squareup.com"
    private val biography = "Engineer on the Point of Sale team."
    private val urlSmall = "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg"
    private val urlBig = "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/large.jpg"
    private val team = "Point of Sale"
    private val type = TYPE.FULL_TIME

    private val employee = Employee(uuid, name, phone, email, biography, urlSmall, urlBig, team, type)

    private val employeeResponse = EmployeeResponse(listOf(employee))

    @Before
    fun begin() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun test_MainFragment_Basic() {
        val scenario = launchFragment<EmployeeMainViewFragment>()
        scenario.onFragment { fragment ->
            fragment.employeeMainViewModel.employeesLiveData.value = employeeResponse.employeeList
            Truth.assertThat(employeeResponse.employeeList.size).isEqualTo(fragment.employeeViewAdapter.itemCount)
            val text = fragment.view?.findViewById<TextView>(R.id.txtNothingToShow)
            Truth.assertThat(text?.visibility).isEqualTo(View.INVISIBLE)
        }
    }

    @Test
    fun test_MainFragment_EmptyList() {
        val scenario = launchFragment<EmployeeMainViewFragment>()
        scenario.onFragment { fragment ->
            fragment.employeeMainViewModel.employeesLiveData.value = emptyList()
            Truth.assertThat(fragment.employeeViewAdapter.itemCount).isEqualTo(0)
            val text = fragment.view?.findViewById<TextView>(R.id.txtNothingToShow)
            Truth.assertThat(text?.visibility).isEqualTo(View.VISIBLE)
        }
    }
}