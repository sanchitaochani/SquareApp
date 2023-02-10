package com.android.square.interview.data

/**
 * A wrapper sealed class that holds different states
 * like success, empty, error, loading.
 */
sealed class ResultWrapper(
    val data: EmployeeResponse? = null,
    val message: String? = null
) {
    class Success(data: EmployeeResponse) : ResultWrapper(data = data)
    class Error(message: String) : ResultWrapper(message = message)
    class Loading : ResultWrapper()
    class Empty(message: String) : ResultWrapper(message = message)
}
