package com.example.androidviewdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidviewdemo.R
import com.example.androidviewdemo.view.ExpandableTitleTextView

public class MyAdapter(val data:List<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_expanable, parent, false))
    }

    override fun getItemCount(): Int {
      return  data.size;
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       if (holder.itemView is ExpandableTitleTextView){
           holder.itemView.setMessage(data.get(position));
       }
    }
}