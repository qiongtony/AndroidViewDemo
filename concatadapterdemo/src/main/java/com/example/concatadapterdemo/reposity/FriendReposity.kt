package com.example.concatadapterdemo.reposity

import com.example.concatadapterdemo.bean.Friend

object FriendReposity {
    val list by lazy {
        mutableListOf(Friend("品超", "广东省", 100), Friend("郭襄", "陕西", 9999),
                Friend("欧阳锋", "大理", -100))
    }
}