package com.example.employeeapplication.data

import com.example.employeeapplication.cache.EmployeeCache
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.model.EmployeeResponse
import com.example.employeeapplication.model.TYPE
import com.example.employeeapplication.network.EmployeeApi
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EmployeeRepoTest {
    @Mock
    private lateinit var employeeApi: EmployeeApi
    @Mock
    private lateinit var employeeCache: EmployeeCache

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
        Mockito.`when`(runBlocking {
            employeeApi.getAllEmployees()
        }).thenReturn(runBlocking {
            employeeResponse
        })
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_EmployeeRepo_getAllEmployees() {
        val employeeRepo = EmployeeRepo(employeeApi, employeeCache)
        runBlockingTest {
            val response = employeeRepo.getAllEmployee()
            Mockito.verify(employeeApi, Mockito.times(1)).getAllEmployees()
            Mockito.verify(employeeCache, Mockito.times(1)).saveAllEmployee(Mockito.anyList())
            Truth.assertThat(response).isEqualTo(employeeResponse.employeeList)
        }
    }
}