package com.example.coreprogramming.lesson3

import com.example.coreprogramming.lesson2.Basis

fun  main(args : Array<String>){
    val basis = Basis()
    basis.testInfix()
}
class Classes {

    // 接口可以1、带默认实现；2、支持抽象属性（如height)，只能通过get方法赋值常量
    interface Flyer{
        // val height = 1000是不对的，应该调用get
        val height : Int
            get() = 1000

        fun kind()

        // 接口带
        fun fly(){
            println("I can fly")
        }
    }
}