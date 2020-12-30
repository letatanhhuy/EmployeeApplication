package com.example.employeeapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.employeeapplication.R
import com.example.employeeapplication.model.Employee
import kotlinx.android.synthetic.main.employee_row_view.view.*

class EmployeeViewAdapter(val itemClickListener:View.OnClickListener) : RecyclerView.Adapter<EmployeeViewAdapter.EmployeeViewHolder>() {

    inner class EmployeeViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var employeeList = emptyList<Employee>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.employee_row_view, parent, false)
        )
    }

    fun updateEmployeeList(list: List<Employee>) {
        employeeList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = employeeList.size


    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(employeeList[position].photoSmall)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) //cache - default also cache so we may don't need to set this
            .error(Glide.with(holder.itemView).load(R.drawable.ic_image_not_found_background))
            .into(holder.itemView.imageEmployee)

        holder.itemView.txtEmployeeName.text = employeeList[position].name
        holder.itemView.txtEmployeeTeam.text = employeeList[position].team
        holder.itemView.txtEmployeeEmail.text = employeeList[position].email

        holder.itemView.setOnClickListener(itemClickListener)
    }

    companion object {
        private const val TAG = "EA:UI:EVA"
    }
}