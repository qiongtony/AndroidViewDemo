package com.example.hencoderwork

import android.util.Log

class Student (val name :String, val grade : Int){
    var className : String = ""
    var ranking : Int = 1

    constructor(name: String, grade: Int, className: String, ranking : Int) : this(name, grade){
        this.className = className;
        this.ranking = ranking;
    }

    constructor(name: String, grade: Int, className: String) : this(name, grade, className, 100){
        Log.i("WWS", "constructor name = $name grade = $grade className = $className ranking = $ranking")
    }

    fun show(){
        Log.i("WWS", "show name = $name grade = $grade className = $className ranking = $ranking")
    }

    fun operator(){
        var list = listOf(21, 40, 11, 33, 78).filter { value -> value % 3 == 0 }
        Log.i("WWS", "operator list = ${list.toString()}")
    }


}