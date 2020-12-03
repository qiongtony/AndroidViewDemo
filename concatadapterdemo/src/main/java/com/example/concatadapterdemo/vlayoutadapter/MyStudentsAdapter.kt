package com.example.concatadapterdemo.vlayoutadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.example.concatadapterdemo.R
import com.example.concatadapterdemo.adapter.StudentAdapter
import com.example.concatadapterdemo.reposity.StudentReposity

class MyStudentsAdapter() : DelegateAdapter.Adapter<StudentAdapter.StudentHolder>(){
    val list by lazy {
        StudentReposity.list
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.StudentHolder =
            StudentAdapter.StudentHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false))

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        return LinearLayoutHelper()
    }

    override fun onBindViewHolder(holder: StudentAdapter.StudentHolder, position: Int) {
        holder.bind(list[position])
    }
}