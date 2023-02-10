package com.android.square.interview.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.square.interview.R
import com.android.square.interview.data.Employee
import com.android.square.interview.data.EmployeeResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class EmployeeListAdapter : RecyclerView.Adapter<EmployeeItemViewHolder>() {
    private val listOfEmployees: MutableList<Employee> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeItemViewHolder {
        return EmployeeItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EmployeeItemViewHolder, position: Int) {
        holder.bind(listOfEmployees[position])
    }

    override fun getItemCount() = listOfEmployees.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<Employee>) {
        if (listOfEmployees.isEmpty()) {
            listOfEmployees.addAll(list)
            notifyDataSetChanged()
        }
    }

}

/**
 * View Holder class for items
 */
class EmployeeItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: Employee) {
        view.findViewById<TextView>(R.id.emp_name).text = item.name
        view.findViewById<TextView>(R.id.emp_team).text = item.team
        view.findViewById<TextView>(R.id.emp_email).text = item.email
        val imgView = view.findViewById<ImageView>(R.id.emp_image)
        Glide.with(view)
            .load(item.url)
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(R.drawable.baseline_account_circle_24)
            .into(imgView)
    }

}