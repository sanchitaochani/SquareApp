package com.android.square.interview.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.square.interview.MainApplication
import com.android.square.interview.R
import com.android.square.interview.data.EmployeeResponse
import com.android.square.interview.data.ResultWrapper
import com.android.square.interview.viewmodel.EmployeeViewModel
import com.android.square.interview.viewmodel.ViewModelFactory
import org.w3c.dom.Text
import javax.inject.Inject

class EmployeeListFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<EmployeeViewModel>
    private val viewModel: EmployeeViewModel by lazy {
        viewModelFactory.get<EmployeeViewModel>(
            requireActivity()
        )
    }

    private lateinit var adapter: EmployeeListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var errorLayout: View
    private lateinit var errorMsg: TextView
    private lateinit var loadingIndicator: ProgressBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MainApplication.getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_employee_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews(view)
        setupRecyclerView()
        setupListener()
        setObservers()
    }

    private fun setupViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeContainer = view.findViewById(R.id.swipeContainer)
        errorLayout = view.findViewById(R.id.errorLayout)
        errorMsg = view.findViewById(R.id.errorDesc)
        loadingIndicator = view.findViewById(R.id.loadingIndicator)
        Toast.makeText(context, R.string.swipe_to_refresh, Toast.LENGTH_LONG).show()
    }

    private fun setupListener() {
        swipeContainer.setOnRefreshListener {
            viewModel.getEmployeeList()
        }
    }

    private fun setupRecyclerView() {
        adapter = EmployeeListAdapter()
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun setObservers() {
        viewModel.result.observe(viewLifecycleOwner) { result ->
            when(result) {
                is ResultWrapper.Error -> showErrorScreen(result.message)
                is ResultWrapper.Success -> showData(result.data)
                is ResultWrapper.Empty -> showErrorScreen(result.message)
                else -> showLoadingScreen()
            }
        }
    }

    /**
     * Ideally I would place all visibility logic in VM instead.
     */

    private fun showData(data: EmployeeResponse?) {
        swipeContainer.isRefreshing = false
        data?.employees?.let { adapter.setItems(it) }
        recyclerView.visibility = View.VISIBLE
        errorLayout.visibility = View.GONE
        loadingIndicator.visibility = View.GONE
    }

    private fun showLoadingScreen() {
        swipeContainer.isRefreshing = false
        loadingIndicator.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorLayout.visibility = View.GONE
    }

    private fun showErrorScreen(message: String?) {
        swipeContainer.isRefreshing = false
        recyclerView.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
        loadingIndicator.visibility = View.GONE
        errorMsg.text = message
    }
}