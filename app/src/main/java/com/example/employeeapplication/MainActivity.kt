package com.example.employeeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.employeeapplication.ui.common.switchView
import com.example.employeeapplication.ui.fragment.EmployeeMainViewFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            switchView(R.id.mainFrame, EmployeeMainViewFragment())
        }
    }
}