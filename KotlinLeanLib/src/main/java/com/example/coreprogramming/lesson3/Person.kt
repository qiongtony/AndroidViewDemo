package com.example.coreprogramming.lesson3

// 定义为data class数据类，可以减少Java中的getter、setter以及hashCode和equal样板代码，那需要用到hashCode和equal时有默认实现吗？
data class Person(var weight : Double, var age : Int, var color : String){
    var sex = 1
    // 自定义的componentN，用在结构 val(weight, age, color, sex) = person
    operator fun component4() :Int{
        return this.sex
    }

    constructor(weight: Double, age: Int, color: String, sex : Int) : this(weight, age, color){
        this.sex = sex;
    }
}