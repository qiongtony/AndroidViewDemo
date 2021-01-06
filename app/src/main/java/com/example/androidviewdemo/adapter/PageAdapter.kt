package com.example.androidviewdemo.adapter


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.androidviewdemo.R
import com.example.androidviewdemo.bean.PageBean

class PageAdapter(val context: Context, val list: List<PageBean<out Activity>>) : RecyclerView.Adapter<PageAdapter.MyHolder>() {


    class MyHolder(root: View) : RecyclerView.ViewHolder(root) {
        val btn: Button by lazy {
            root.findViewById<Button>(R.id.btn)
        }

        fun bind(page: PageBean<out Activity>) {
            btn.text = page.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.item_page, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(list[position])
        holder.btn.setOnClickListener {
            context.startActivity(Intent(context, list[position].clazz))
        }
    }
}