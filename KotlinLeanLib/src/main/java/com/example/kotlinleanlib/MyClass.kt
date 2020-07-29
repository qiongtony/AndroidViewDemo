package com.example.kotlinleanlib

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
}

class MyClass {
    fun main(args : Array<String>){
        val test = Test()
        test.evalWithLogging(Test.Sum(Test.Sum(Test.Num(1), Test.Num(2)), Test.Num(3)));
    }
}