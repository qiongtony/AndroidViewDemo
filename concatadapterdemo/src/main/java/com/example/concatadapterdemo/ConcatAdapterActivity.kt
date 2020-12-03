package com.example.concatadapterdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.concatadapterdemo.adapter.FriendsAdapter
import com.example.concatadapterdemo.adapter.StudentAdapter
import com.example.concatadapterdemo.bean.Friend
import kotlinx.android.synthetic.main.activity_concat.*

class ConcatAdapterActivity : AppCompatActivity() {
    val friendsAdapter by lazy {
        FriendsAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concat)
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = ConcatAdapter()
        adapter.addAdapter(friendsAdapter)
        adapter.addAdapter(StudentAdapter())
        recycler_view.adapter = adapter

        btn_add_friend.setOnClickListener {
            val oldPos = friendsAdapter.list.size
            friendsAdapter.list.add(Friend("吴伟山", "广东省梅州市", 100))
            friendsAdapter.notifyItemInserted(oldPos)
        }
    }
}
