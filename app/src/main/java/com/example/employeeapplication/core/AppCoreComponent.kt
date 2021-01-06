package com.example.employeeapplication.core

import android.content.Context
import android.content.SharedPreferences
import com.example.employeeapplication.data.EmployeeRepo
import com.example.employeeapplication.model.AnnotatedDeserializer
import com.example.employeeapplication.model.Employee
import com.example.employeeapplication.network.EmployeeApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppCoreComponent() {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            connectTimeout(MAX_CONNECTION_TIME, TimeUnit.SECONDS)
            readTimeout(MAX_READ_TIME, TimeUnit.SECONDS)
        }.build()
    }

    private val employeeApi: EmployeeApi by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(EmployeeApi::class.java)
    }

    private val employeeRepo: EmployeeRepo by lazy {
        EmployeeRepo(employeeApi)
    }


    private val gson: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(Employee::class.java, AnnotatedDeserializer<Employee>())
            .create()

    }

    fun getApplicationGSON(): Gson = gson
    fun getApplicationEmployeeApi(): EmployeeApi = employeeApi
    fun getApplicationEmployeeRepo(): EmployeeRepo = employeeRepo

    companion object {
        private const val MAX_CONNECTION_TIME = 30L
        private const val MAX_READ_TIME = 30L
        private const val BASE_URL = "https://s3.amazonaws.com/sq-mobile-interview/"
    }
}