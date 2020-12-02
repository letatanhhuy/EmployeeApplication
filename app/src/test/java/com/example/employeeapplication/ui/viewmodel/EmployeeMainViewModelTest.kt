package com.example.employeeapplication.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.employeeapplication.data.EmployeeRepo
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.model.EmployeeResponse
import com.example.employeeapplication.model.TYPE
import com.example.employeeapplication.ui.MainCoroutineScopeRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.Exception

@ExperimentalCoroutinesApi
class EmployeeMainViewModelTest {
    @Mock
    private lateinit var employeeRepo: EmployeeRepo
    @Mock
    private lateinit var employeesLiveData: MutableLiveData<List<Employee>>
    @Mock
    private lateinit var loadingData: MutableLiveData<Boolean>
    @Mock
    private lateinit var showError: MutableLiveData<Boolean>


    private val coroutineScope = MainCoroutineScopeRule()

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
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(employeesLiveData.postValue(Mockito.any())).then {  }
        Mockito.`when`(loadingData.postValue(Mockito.any())).then {  }
        Mockito.`when`(showError.postValue(Mockito.any())).then {  }
        Mockito.`when`(runBlocking {
            employeeRepo.getAllEmployee()
        }).thenReturn(runBlocking {
            employeeResponse.employeeList
        })
    }

    @Test
    fun test_EmployeeMainViewModel_init() {
        val mainViewModel = EmployeeMainViewModel(employeeRepo, coroutineScope.dispatcher)
        Truth.assertThat(mainViewModel.employeesLiveData).isNotNull()
        Truth.assertThat(mainViewModel.loadingData.value).isFalse()
        Truth.assertThat(mainViewModel.showError.value).isFalse()
    }

    @Test
    fun test_EmployeeMainViewModel_getAllEmployee_success() {
        val employeeMainViewModel = EmployeeMainViewModel(employeeRepo, coroutineScope.dispatcher)
        runBlockingTest {
            employeeMainViewModel.getAllEmployee(employeesLiveData, loadingData, showError)
            Mockito.verify(employeeRepo, Mockito.times(1)).getAllEmployee()
            Mockito.verify(employeesLiveData, Mockito.times(1)).postValue(Mockito.any())
            Mockito.verify(loadingData, Mockito.times(1)).postValue(true)
            Mockito.verify(loadingData, Mockito.times(1)).postValue(false)
            Mockito.verify(showError, Mockito.times(1)).postValue(false)
            Mockito.verify(showError, Mockito.times(0)).postValue(true)
        }
    }

    @Test
    fun test_EmployeeMainViewModel_getAllEmployee_fail() {
        Mockito.`when`(runBlocking {
            employeeRepo.getAllEmployee()
        }).then {
            runBlocking {
                throw Exception() //simulate error
            }
        }
        val employeeMainViewModel = EmployeeMainViewModel(employeeRepo, coroutineScope.dispatcher)
        runBlockingTest {
            employeeMainViewModel.getAllEmployee(employeesLiveData, loadingData, showError)
            Mockito.verify(employeeRepo, Mockito.times(1)).getAllEmployee()
            Mockito.verify(employeesLiveData, Mockito.times(1)).postValue(emptyList())
            Mockito.verify(loadingData, Mockito.times(1)).postValue(true)
            Mockito.verify(loadingData, Mockito.times(1)).postValue(false)
            Mockito.verify(showError, Mockito.times(1)).postValue(false)
            Mockito.verify(showError, Mockito.times(1)).postValue(true)
        }
    }

    @Test
    fun test_MainViewModel_getAllEmployee_whileAlreadyLoading() {
        Mockito.`when`(loadingData.value).thenReturn(true)
        val employeeMainViewModel = EmployeeMainViewModel(employeeRepo, coroutineScope.dispatcher)
        runBlockingTest {
            employeeMainViewModel.getAllEmployee(employeesLiveData, loadingData, showError)
            Mockito.verify(employeeRepo, Mockito.times(0)).getAllEmployee()
            Mockito.verify(employeesLiveData, Mockito.times(0)).postValue(Mockito.any())
            Mockito.verify(loadingData, Mockito.times(0)).postValue(Mockito.anyBoolean())
            Mockito.verify(showError, Mockito.times(0)).postValue(Mockito.anyBoolean())
        }
    }
}