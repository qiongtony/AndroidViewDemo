package com.example.androidviewdemo.repo

import android.app.Activity
import com.example.androidviewdemo.activity.BottomSheetDialogActivity
import com.example.androidviewdemo.activity.ClassSevenTestActivity
import com.example.androidviewdemo.activity.ExpanableTextActivity
import com.example.androidviewdemo.bean.PageBean

object PageRepo {
    val list: List<PageBean<out Activity>> by lazy {
        listOf(
                PageBean("跳转到ExpanableText", ExpanableTextActivity::class.java),
                PageBean("跳转到第7节作业", ClassSevenTestActivity::class.java),
                PageBean("跳转到自定义BottomSheetDialog", BottomSheetDialogActivity::class.java)
        )
    }

}