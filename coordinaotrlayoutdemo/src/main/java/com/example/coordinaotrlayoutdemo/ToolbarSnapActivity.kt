package com.example.coordinaotrlayoutdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coordinaotrlayoutdemo.adapter.MyAdapter
import com.google.android.material.appbar.AppBarLayout

/**
 * layout_scrollFlags:
 * scroll:跟着滑动，不设置，则会导致这部分固定，其实就相当于普通的View了
 * enterAlways：一下滑的时候就会显示出来
 */
class ToolbarSnapActivity : AppCompatActivity() {
    lateinit var recyclerView : RecyclerView
    lateinit var toolbar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar_with_coordinator_layout_snap)
        toolbar = findViewById(R.id.tool_bar)
        recyclerView = findViewById(R.id.recycler)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapter = MyAdapter(this)
        recyclerView.adapter = adapter
    }
}