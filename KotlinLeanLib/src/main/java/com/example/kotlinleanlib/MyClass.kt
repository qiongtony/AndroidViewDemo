package com.example.kotlinleanlib

fun  main(args : Array<String>){
    val test = Test()
    test.evalWithLogging(Test.Sum(Test.Sum(Test.Num(1), Test.Num(2)), Test.Num(3)));
//    test.testFizzBuzz()
//    test.testFizzBuzzDownTo()
    test.testForByUtil()
}

class MyClass {
    fun main(args : Array<String>){
        val test = Test()
        test.evalWithLogging(Test.Sum(Test.Sum(Test.Num(1), Test.Num(2)), Test.Num(3)));
    }
}