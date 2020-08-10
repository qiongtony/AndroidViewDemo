package com.example.hencoderwork

import android.util.Log

/**
 * 类的执行顺序：
 * 主构造器->init->次构造器
 */
class User(val name :String, val age : Int) {
    var location : String = ""
    init {
        Log.i("WWS", "init name = $name age = $age")
    }

    constructor(name: String, age: Int, location: String) : this(name, age){
        this.location = location
        Log.i("WWS", "次构造器 name = $name age = $age")
    }

    fun sayHi(name : String = "world", test : Int = 10,age : Int){
        Log.i("WWS", "name = $name test = $test age = $age")
    }
}