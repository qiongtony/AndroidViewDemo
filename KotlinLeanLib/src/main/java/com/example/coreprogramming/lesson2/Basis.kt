package com.example.coreprogramming.lesson2

fun  main(args : Array<String>){
    val basis = Basis()
    basis.testInfix()
}
class Basis {

    // 用"="可以省略返回值类型
    fun sum(a : Int, b : Int) = if(a > b) a else b

    // Kotlin无法进行全局类型推导，所以递归调用需要显示声明返回值类型
//    fun foo(n : Int) = if(n == 0) 1 else foo(n - 1)
    fun foo(n : Int) : Int {
        return if (n == 0)
            1
        else
            foo(n -1)
    }

    fun testFunVar(){
        val countryApp = CountryApp()
        val countryTest = CountryTest()
        val countries = listOf<Country>()
        // ::让函数或构造器变成对象，这样可以当成函数参数来传递
        countryApp.filterCountries(countries, countryTest::isBigEuropeanCountry)
    }

    // Elvis运算符：为什么要用？可以用?声明可空变量->需要处理为空情况下的值，用"?:"来处理为空时的默认值，类似与getOrElse
    fun testElvis(){
        val maybeInt : Int? = null
        print({maybeInt ?: 1})
    }

    enum class DayOfWeek(val day : Int){
        MON(1),
        TUE(2),
        WEN(3),
        THU(4),
        FRI(5),
        SAT(6),
        SUN(7)
        ;// 类型后有额外的属性或方法，必须带";"
        fun getDayOfNumber() = day
    }

    // 中缀表达式 in、step、downTo、until
    // 可变参数
    fun testInfix(){
        val letters = arrayOf("a", "b", "c")
        // "*"来使用可变参数
        printLetters(*letters, count = 3)

        val p = Person()
        // 调用形式：a 表达式名称 参数
        p called "Wuweishan"
    }
    fun printLetters(vararg letters : String, count : Int){
        print("$count")
        for (letter in letters) print(letter)
        println()
    }

    class Person{
        // 声明为中缀表达式
        infix fun called(name: String){
            println("My name is $name")
        }
    }
}
