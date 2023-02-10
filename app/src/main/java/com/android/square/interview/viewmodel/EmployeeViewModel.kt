package com.android.square.interview.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.android.square.interview.data.EmployeeResponse
import com.android.square.interview.data.ResultWrapper
import com.android.square.interview.network.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class EmployeeViewModel @Inject constructor(
    private val repository: Repository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    /**
     * Mutable Live Data to hold result.
     */
    private val _result = MutableLiveData<ResultWrapper>()

    /**
     * Public Live Data that is exposed.
     */
    val result: LiveData<ResultWrapper> = _result

    /**
     * Currently, I have hardcoded some strings.
     * Normally I would pass resources as a DI and use that to fetch string resources.
     */
    companion object {
        const val TRY_AGAIN_MSG = "Something strange happened, keep calm and try again."
        const val NO_EMPLOYEES_FOUND = "That's weird, no employees found."
        const val MALFORMED_DATA_MSG = "Hmm, looks like the data we have is corrupted. Why don't you try again?"
    }

    fun getEmployeeList() {
        viewModelScope.launch {
            _result.postValue(ResultWrapper.Loading())
            val response = withContext(ioDispatcher) {
                repository.getList()
            }
            response.enqueue(object : Callback<EmployeeResponse> {
                override fun onResponse(
                    call: Call<EmployeeResponse>,
                    response: Response<EmployeeResponse>
                ) {
                    if (!response.isSuccessful) {
                        _result.postValue(ResultWrapper.Error(TRY_AGAIN_MSG))
                    }
                    if (response.body() == null) {
                        _result.postValue(ResultWrapper.Error(NO_EMPLOYEES_FOUND))
                    }
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        if (responseBody.employees.isEmpty()) {
                            _result.postValue(ResultWrapper.Empty(NO_EMPLOYEES_FOUND))
                        } else {
                            val isMalformed = validateFields(responseBody)
                            if (!isMalformed) {
                                _result.postValue(ResultWrapper.Success(responseBody))
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<EmployeeResponse>, t: Throwable) {
                    // for debugging purposes only
                    Log.i("EmployeeViewModel", t.message.toString())
                    _result.postValue(ResultWrapper.Error(TRY_AGAIN_MSG))
                }
            })
        }
    }

    /**
     * Invalidate data if necessary fields are null/blank.
     * Since we are working with a small dataset, this isn't much of a problem
     * but in production code, it's likely that the json response will be large and iterating
     * over all fields is far from ideal.
     * Maybe there is a way to intercept the JSON parsing and invalidate
     * as it gets parsed, but a more realistic solution is to only invalidate
     * only the items that have malformed data OR set placeholder messages for
     * missing data.
     * Also, unsure how we can figure out if the data isn't missing
     * but just incorrect.
     */
    private fun validateFields(responseBody: EmployeeResponse): Boolean {
        for (item in responseBody.employees) {
            if (item.name.isNullOrEmpty() || item.team.isNullOrEmpty() || item.email.isNullOrEmpty()) {
                _result.postValue(ResultWrapper.Empty(MALFORMED_DATA_MSG))
                return true
            }
        }
       return false
    }

    init {
        getEmployeeList()
    }
}