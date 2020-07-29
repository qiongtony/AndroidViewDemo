package com.example.kotlinleanlib
// java转为kotlin，kotlin默认是public的
//class Person(val name: String)

class Person{
    /**
     * 不可变类型，默认只有getter
     */
    val name : String = "haha"

    /**
     * 可变属性，getter和setter都有
     */
    var isMarried :Boolean = false
}