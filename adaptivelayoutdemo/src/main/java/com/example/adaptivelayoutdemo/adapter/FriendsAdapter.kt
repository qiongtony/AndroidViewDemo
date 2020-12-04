package com.example.concatadapterdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adaptivelayoutdemo.R
import com.example.concatadapterdemo.bean.Friend
import com.example.concatadapterdemo.reposity.FriendReposity

class FriendsAdapter() : RecyclerView.Adapter<FriendsAdapter.FriendsHolder>() {
    val list by lazy {
        FriendReposity.list
    }

    open class FriendsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName by lazy {
            itemView.findViewById<TextView>(R.id.tv_name)
        }

        val tvAddress by lazy {
            itemView.findViewById<TextView>(R.id.tv_address)
        }

        val tvIntimacy by lazy {
            itemView.findViewById<TextView>(R.id.tv_intimacy)
        }

        fun bind(friend: Friend) {
            friend.apply {
                tvName.text = name
                tvAddress.text = address
                tvIntimacy.text = intimacy.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsHolder {
        return FriendsHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FriendsHolder, position: Int) {
        holder.bind(list[position])
    }
}