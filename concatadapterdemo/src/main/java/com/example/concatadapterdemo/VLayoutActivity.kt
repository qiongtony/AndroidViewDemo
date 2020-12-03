package com.example.concatadapterdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.example.concatadapterdemo.bean.Friend
import com.example.concatadapterdemo.vlayoutadapter.MyFriendsAdapter
import com.example.concatadapterdemo.vlayoutadapter.MyStudentsAdapter
import kotlinx.android.synthetic.main.activity_concat.*
import java.util.*

class VLayoutActivity : AppCompatActivity() {
    val myFriendsAdapter: MyFriendsAdapter by lazy {
        MyFriendsAdapter()
    }
    val myStudentsAdapter: MyStudentsAdapter by lazy {
        MyStudentsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concat)

        /**
         * 步骤1、设置ViewPool
         */
        val viewPool = RecyclerView.RecycledViewPool()
        recycler_view.setRecycledViewPool(viewPool)
        viewPool.setMaxRecycledViews(0, 10)

        /**
         * 步骤2、创建VirtualLayoutManager
         */
        val layoutManager = VirtualLayoutManager(this, OrientationHelper.VERTICAL, false)
        recycler_view.layoutManager = layoutManager

        /**
         * 步骤3、创建DelegateAdapter将所有的adapter的VLM都射进来，给RV设置adapter
         *
         */
        val adapters = LinkedList<DelegateAdapter.Adapter<out RecyclerView.ViewHolder>>()
        adapters.add(myFriendsAdapter)
        adapters.add(myStudentsAdapter)

        val delegateAdapter = DelegateAdapter(layoutManager)
        delegateAdapter.setAdapters(adapters)

        recycler_view.adapter = delegateAdapter

        // 给MyFriendsAdapter添加数据
        btn_add_friend.setOnClickListener {
            val oldPos = myFriendsAdapter.list.size
            myFriendsAdapter.list.add(Friend("吴伟山", "广东省梅州市", 100))
            myFriendsAdapter.notifyItemInserted(oldPos)
        }
    }
}