package com.android.square.interview.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.square.interview.data.Employee
import com.android.square.interview.data.EmployeeResponse
import com.android.square.interview.data.ResultWrapper
import com.android.square.interview.network.Repository
import junit.framework.TestCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*
import retrofit2.Call
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EmployeeViewModelTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var call: Call<EmployeeResponse>

    private lateinit var viewModel: EmployeeViewModel

    @Mock
    lateinit var ioDispatcher: CoroutineDispatcher

    @Mock
    lateinit var observer: Observer<ResultWrapper>

    private val mockList = listOf(
        Employee("Joe", "core", "http://xyz", "abc@g.com"),
        Employee("Sam", "HR", "http://swuhd","wns@gd.co"),
        Employee("Pat", "platform", "http://ahgw", "s8ek@h.co")
    )

    private val malformedList = listOf(
        Employee("Joe", "", "http://xyz", "abc@g.com"),
        Employee("", "HR", "http://swuhd","wns@gd.co"),
        Employee("Pat", "platform", "", "s8ek@h.co")
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        // viewModel = EmployeeViewModel(repository, ioDispatcher)
    }

    @Test
    fun testSuccessButEmptyList() = mainCoroutineRule.runBlockingTest {
        val emptyList = EmployeeResponse(listOf())
        val response = Response.success(emptyList)
        whenever(repository.getList()) doReturn call
        whenever(call.execute()).thenReturn(response)
        viewModel = EmployeeViewModel(repository, ioDispatcher)
        viewModel.result.observeForever { observer }
        viewModel.getEmployeeList()
        // verify(observer).onChanged(ResultWrapper.Loading())
        // verify(repository).getList()
        // verify(observer).onChanged(ResultWrapper.Empty("No employees found"))
    }

    @Test
    fun testSuccessWithCompleteList() = mainCoroutineRule.runBlockingTest {
        val list = EmployeeResponse(mockList)
        val response = Response.success(list)
        whenever(repository.getList()) doReturn call
        whenever(call.execute()).thenReturn(response)
        viewModel = EmployeeViewModel(repository, ioDispatcher)
        viewModel.result.observeForever { observer }
        viewModel.getEmployeeList()
        // verify(observer).onChanged(ResultWrapper.Loading())
        // verify(repository).getList()
        // verify(observer).onChanged(ResultWrapper.Success(list))
    }

    @Test
    fun testSuccessWithMalformedList() = mainCoroutineRule.runBlockingTest {
        val list = EmployeeResponse(malformedList)
        val response = Response.success(list)
        whenever(repository.getList()) doReturn call
        whenever(call.execute()).thenReturn(response)
        viewModel = EmployeeViewModel(repository, ioDispatcher)
        viewModel.result.observeForever { observer }
        viewModel.getEmployeeList()
        // verify(observer).onChanged(ResultWrapper.Loading())
        // verify(repository).getList()
        // verify(observer).onChanged(ResultWrapper.Empty("Malformed data"))
    }

}