package com.example.employeeapplication.model

import com.google.common.truth.Truth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class EmployeeTest {
    private val uuid = "0d8fcc12-4d0c-425c-8355-390b312b909c"
    private val name = "Justine Mason"
    private val phone = "5553280123"
    private val email = "jmason.demo@squareup.com"
    private val biography = "Engineer on the Point of Sale team."
    private val urlSmall = "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg"
    private val urlBig = "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/large.jpg"
    private val team = "Point of Sale"
    private val type = TYPE.FULL_TIME
    private val gson: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(Employee::class.java, AnnotatedDeserializer<Employee>())
            .create()
    }
    private val gsonEmployee = "{\n" +
            "  \"uuid\" : \"0d8fcc12-4d0c-425c-8355-390b312b909c\",\n" +
            "  \"full_name\" : \"Justine Mason\",\n" +
            "  \"phone_number\" : \"5553280123\",\n" +
            "  \"email_address\" : \"jmason.demo@squareup.com\",\n" +
            "  \"biography\" : \"Engineer on the Point of Sale team.\",\n" +
            "  \"photo_url_small\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg\",\n" +
            "  \"photo_url_large\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/large.jpg\",\n" +
            "  \"team\" : \"Point of Sale\",\n" +
            "  \"employee_type\" : \"FULL_TIME\"\n" +
            "}"

    private val gsonEmployeeMissing = "{\n" +
            "  \"uuid\" : \"0d8fcc12-4d0c-425c-8355-390b312b909c\",\n" +
            "  \"phone_number\" : \"5553280123\",\n" +
            "  \"email_address\" : \"jmason.demo@squareup.com\",\n" +
            "  \"biography\" : \"Engineer on the Point of Sale team.\",\n" +
            "  \"photo_url_small\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg\",\n" +
            "  \"photo_url_large\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/large.jpg\",\n" +
            "  \"team\" : \"Point of Sale\",\n" +
            "  \"employee_type\" : \"FULL_TIME\"\n" +
            "}"
    @Before
    fun setUp() {

    }

    @Test
    fun test_Employee_constructor() {
        val employee = Employee(uuid, name, phone, email, biography, urlSmall, urlBig, team, type)
        Truth.assertThat(employee.uuid).isEqualTo(uuid)
        Truth.assertThat(employee.name).isEqualTo(name)
        Truth.assertThat(employee.phone).isEqualTo(phone)
        Truth.assertThat(employee.email).isEqualTo(email)
        Truth.assertThat(employee.biography).isEqualTo(biography)
        Truth.assertThat(employee.photoSmall).isEqualTo(urlSmall)
        Truth.assertThat(employee.photoLarge).isEqualTo(urlBig)
        Truth.assertThat(employee.team).isEqualTo(team)
        Truth.assertThat(employee.type).isEqualTo(type)
    }

    @Test
    fun test_Employee_FromGson() {
        val employee = Employee(uuid, name, phone, email, biography, urlSmall, urlBig, team, type)
        val employeeFromGson = gson.fromJson(gsonEmployee, Employee::class.java)

        Truth.assertThat(employee.uuid).isEqualTo(employeeFromGson.uuid)
        Truth.assertThat(employee.name).isEqualTo(employeeFromGson.name)
        Truth.assertThat(employee.phone).isEqualTo(employeeFromGson.phone)
        Truth.assertThat(employee.email).isEqualTo(employeeFromGson.email)
        Truth.assertThat(employee.biography).isEqualTo(employeeFromGson.biography)
        Truth.assertThat(employee.photoSmall).isEqualTo(employeeFromGson.photoSmall)
        Truth.assertThat(employee.photoLarge).isEqualTo(employeeFromGson.photoLarge)
        Truth.assertThat(employee.team).isEqualTo(employeeFromGson.team)
        Truth.assertThat(employee.type).isEqualTo(employeeFromGson.type)
    }

    @Test
    fun test_Employee_FromGson_MissingRequiredField() {
        val employeeFromGson:Employee? = try {
            gson.fromJson(gsonEmployee, Employee::class.java)
        } catch (e:Exception) {
            null
        }
        Truth.assertThat(employeeFromGson).isNotNull()
        val employeeFromGsonMissing:Employee? = try {
            gson.fromJson(gsonEmployeeMissing, Employee::class.java)
        } catch (e:Exception) {
            null
        }
        Truth.assertThat(employeeFromGsonMissing).isNull()
    }
}