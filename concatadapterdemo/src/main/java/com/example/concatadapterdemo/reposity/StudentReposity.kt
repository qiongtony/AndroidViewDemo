package com.example.concatadapterdemo.reposity

import com.example.concatadapterdemo.bean.Student

object StudentReposity {
    val list by lazy {
        mutableListOf(Student("郭超", 1, 5), Student("荀彧", 6, 1), Student("品管", 2, 2))
    }


}