package com.example.concatadapterdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adaptivelayoutdemo.R
import com.example.concatadapterdemo.bean.Student
import com.example.concatadapterdemo.reposity.StudentReposity

class StudentAdapter() : RecyclerView.Adapter<StudentAdapter.StudentHolder>() {
    val list by lazy {
        StudentReposity.list
    }

    class StudentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName by lazy {
            itemView.findViewById<TextView>(R.id.tv_name)
        }

        val tvGrade by lazy {
            itemView.findViewById<TextView>(R.id.tv_grade)
        }

        val tvRank by lazy {
            itemView.findViewById<TextView>(R.id.tv_rank)
        }

        fun bind(student: Student) {
            student.apply {
                tvName.text = name
                tvGrade.text = grade.toString()
                tvRank.text = rank.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        return StudentHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {
        holder.bind(list.get(position))
    }
}