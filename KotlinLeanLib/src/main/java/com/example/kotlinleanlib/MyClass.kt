package com.example.kotlinleanlib

import java.util.*

fun  main(args : Array<String>){
//    val test = Test()
//    test.evalWithLogging(Test.Sum(Test.Sum(Test.Num(1), Test.Num(2)), Test.Num(3)));
//    test.testFizzBuzz()
//    test.testFizzBuzzDownTo()
//    test.testForByUtil()

    val mapTet = MapTest()
//    mapTet.execute()
//    mapTet.forWithIndex()
    println(mapTet.recognize('1'))
    println(mapTet.recognize('A'))
    println(mapTet.recognize('@'))

    val list = listOf<Int>(1, 2, 3)
    println(joinToString(list, "；", "（", "）"))
    println(joinToString(list))
    println(joinToString(list, "；"))
    println(joinToString(list))

    // 这样可以扩展原有类有点牛逼啊
    println("Kotlin".lastChar())

    println(list.joinToStringFinal("；", "（", "）"))

    // 扩展函数调用依据的是声明类型
   var test : Vector<String> = Stack<String>()
    test.test()

    variableParams(arrayOf("Java", "Kotlin"))

    stringSplit()

    val user = User(123, "吴伟山", "广东省")
    saveUser(user)
}

class MyClass {
    fun main(args : Array<String>){
        val test = Test()
        test.evalWithLogging(Test.Sum(Test.Sum(Test.Num(1), Test.Num(2)), Test.Num(3)));
    }
}