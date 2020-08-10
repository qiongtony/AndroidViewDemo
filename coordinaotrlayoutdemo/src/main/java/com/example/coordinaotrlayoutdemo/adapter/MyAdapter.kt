package com.example.coordinaotrlayoutdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coordinaotrlayoutdemo.R
import com.example.coordinaotrlayoutdemo.repo.UserRepo

class MyAdapter(val context : Context) : RecyclerView.Adapter<MyHolder>() {
    private val datas : List<UserRepo.User> = UserRepo.getUsers()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.item_mine, parent, false))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.tvContent.text = datas[position].content
    }
}

class MyHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val tvContent : TextView
    init {
        tvContent = itemView.findViewById(R.id.tv_content)
    }

}