package com.example.actualcompat

import com.example.coreprogramming.lesson2.Basis

/**
 * 作用域函数的共同特点：
 * 在一个对象上执行一个代码块
 *
 * 不同点：
 * 引用上下文对象的方式：this还是it（lambda的接收者是this，参数是it）
 * 返回值
 *
 * run、apply用this或省略
 * let、also
 *
 * 返回值：
 * 上下文对象：apply、also
 * lambda表达式结果:let、run和with
 */
class ScopeFun {


    fun letTest(){
        Person("Alice", 20, "Amsterdam").let {
            println(it)
            it.moveTo("London")
            it.incrementAge()
            println(it)
        }
    }

    fun thisOrIt(){
        Person("Alice", 20, "Amsterdam").run {
            incrementAge()
            this.incrementAge()
            1
        }
        Person("Alice", 20, "Amsterdam").apply {
            incrementAge()
        }

        Person("Alice", 20, "Amsterdam").let {
            it.incrementAge()
        }

        Person("Alice", 20, "Amsterdam").also {
            it.incrementAge()
        }
    }
}

fun  main(args : Array<String>){
    val test = ScopeFun()
    test.letTest()

    val numbers = mutableListOf("one", "two", "three")
    val countEndsWithE = numbers.run {
        add("four")
        add("five")
        count {
            it.endsWith("e")
        }
    }
    println("There are $countEndsWithE elements that end with e.")

    // 用于减少非空的判断
    val str:String? = null
    val length = str?.let {
        println("let() called on $it")
        it.length
    }
    println("length = $length")
}