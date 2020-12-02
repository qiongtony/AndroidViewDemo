package com.example.actualcompat

fun testLambada(i : Int = 1, j : Int = 1, compute : (x :Int, y : Int)-> Unit){
    compute(i, j)
}

fun callback(a :Int, b: Int) : Unit{
    println("a = $a, b = $b")
}

/**
 * 结果：
 * 1 - 1 = 0
 * 2 + 4 = 6
 * a = 2, b = 6
 */
fun testRun(){
    testLambada { x, y -> println("$x - $y = ${x - y}") }

    testLambada(2, 4){
        x, y ->
        println("$x - $y = ${x + y}")
    }
    testLambada(2, 6, ::callback)
}