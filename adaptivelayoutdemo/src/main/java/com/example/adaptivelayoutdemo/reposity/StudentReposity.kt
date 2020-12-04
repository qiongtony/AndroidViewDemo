package com.example.concatadapterdemo.reposity

import com.example.concatadapterdemo.bean.Student

object StudentReposity {
    val list by lazy {
        mutableListOf(Student("郭超", 1, 5), Student("荀彧", 6, 1),
                Student("品管", 2, 2), Student("彭万里", 2, 2),
                Student("张伍绍祖", 2, 2), Student("高尚德", 2, 2),
                Student("刘乃超", 2, 2),   Student("张国柱", 2, 2),
                Student("孙旭诚", 2, 2),   Student("孙宏坤 ", 2, 2),
                Student("孙小权", 2, 2),   Student("万博容 ", 2, 2),
                Student("吕伟博", 2, 2),   Student("石修诚 ", 2, 2)
        )
    }


}