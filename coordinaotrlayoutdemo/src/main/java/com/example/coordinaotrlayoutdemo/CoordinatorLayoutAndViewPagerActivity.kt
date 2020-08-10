package com.example.coordinaotrlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.coordinaotrlayoutdemo.adapter.MyFragmentPagerAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout

/**
 * 这么做会有问题，只有顶部可以上滑
 * 使用CollapsingToolbarLayout实现视差效果（parallax)以及上滑Toolbar固定的效果(ping)
 * parallax:视差
 * pin:上滑离开顶部，最终会固定在显示的顶部
 */
class CoordinatorLayoutAndViewPagerActivity : AppCompatActivity(), TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
    lateinit var viewPager: ViewPager
    lateinit var tagLayout: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_layout_and_view_pager)
        viewPager = findViewById(R.id.view_pager)
        tagLayout = findViewById(R.id.tags)

//        val temp =  tagLayout.newTab()
//        temp.text = "主页"
//        tagLayout.addTab(temp)
//
//        val temp2 = tagLayout.newTab()
//        temp2.text = "微博"
//        tagLayout.addTab(temp2)
//
//        val temp3 = tagLayout.newTab()
//        temp3.text = "相册"
//        tagLayout.addTab(temp3)

        tagLayout.addOnTabSelectedListener(this)

        viewPager.adapter = MyFragmentPagerAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT)
        tagLayout.setupWithViewPager(viewPager)
    }

    override fun onTabReselected(p0: TabLayout.Tab?) {

    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {
    }

    override fun onTabSelected(p0: TabLayout.Tab?) {
    }
}