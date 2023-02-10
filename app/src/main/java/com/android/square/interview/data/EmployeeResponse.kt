package com.android.square.interview.data

import com.google.gson.annotations.SerializedName

/**
 * Data class for JSON response object.
 */
data class EmployeeResponse(
    @SerializedName("employees")
    val employees: List<Employee>
)
