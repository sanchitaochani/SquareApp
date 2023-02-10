package com.android.square.interview.network

import com.android.square.interview.data.EmployeeResponse
import retrofit2.Call
import retrofit2.http.GET

/**
 * Retrofit Service interface.
 */
interface RetrofitService {
    @GET("employees.json")
    fun getListOfEmployees() : Call<EmployeeResponse>
}