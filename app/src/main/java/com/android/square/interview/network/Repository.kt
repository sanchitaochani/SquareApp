package com.android.square.interview.network

import com.android.square.interview.data.EmployeeResponse
import kotlinx.coroutines.delay
import retrofit2.Call
import javax.inject.Inject

class Repository @Inject constructor(
    private val service: RetrofitService
) {
    suspend fun getList(): Call<EmployeeResponse> {
        // Delay for testing loading logic
        // delay(5000)
        return service.getListOfEmployees()
    }
}