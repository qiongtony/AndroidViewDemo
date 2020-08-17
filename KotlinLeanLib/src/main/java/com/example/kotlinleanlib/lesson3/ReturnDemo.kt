package com.example.kotlinleanlib.lesson3

class ReturnDemo {
    fun  main(args : Array<String>){
        test()
    }
    fun sum(a : Int, b : Int) : Int{
        return a + b
    }

    fun max(a :Int, b : Int) : Int {
        return if (a > b)
            a
        else
            b
    }

    fun sumShort(a : Int, b :Int) = a + b

    fun maxShort(a : Int, b : Int)  = if(a > b) a else b

    fun test(){
        // 函数返回值可以当成变量
        val sum = fun(a : Int, b : Int) = a + b
        println("sum(1, 1) = ${sum(1, 1)}")

        // 带了大括号就
        val sumf = fun(a : Int, b : Int) = {a + b}
        println("sumf = $sumf sumf(1,1) = ${sumf(1, 1)} sumf(1, 1).invoke() = ${sumf(1, 1).invoke()}")

    }

    // return语句会从最近函数的函数中返回，如果在lambda调用返回不是跳出lambda表达式，而是找最近的函数返回！
    fun return1(){
        println("START ")
        val intArray = intArrayOf(1, 2, 3, 4, 5)
        intArray.forEach {
            if (it == 3) return
            println(it)
        }
        // 打印结果1，2，与break类似
        println("END")
    }

    fun return2(){
        println("START ")
        val intArray = intArrayOf(1, 2, 3, 4, 5)
        // 打印结果1，2，4，5，与cotinue类似
        intArray.forEach {fun (a : Int){
            if (a == 3) return
            println(a)
        } }
        println("END")
    }


}