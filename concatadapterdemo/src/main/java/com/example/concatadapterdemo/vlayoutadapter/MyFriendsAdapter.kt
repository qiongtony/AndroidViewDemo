package com.example.concatadapterdemo.vlayoutadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.example.concatadapterdemo.R
import com.example.concatadapterdemo.adapter.FriendsAdapter
import com.example.concatadapterdemo.reposity.FriendReposity

class MyFriendsAdapter() : DelegateAdapter.Adapter<FriendsAdapter.FriendsHolder>(){
    val list by lazy {
        FriendReposity.list
    }
    override fun onCreateLayoutHelper(): LayoutHelper = LinearLayoutHelper()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsAdapter.FriendsHolder =
            FriendsAdapter.FriendsHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: FriendsAdapter.FriendsHolder, position: Int) {
       holder.bind(list.get(position))
    }
}