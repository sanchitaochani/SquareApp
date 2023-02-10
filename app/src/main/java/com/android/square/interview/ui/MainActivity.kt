package com.android.square.interview.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.square.interview.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, EmployeeListFragment())
            .commit()
    }
}