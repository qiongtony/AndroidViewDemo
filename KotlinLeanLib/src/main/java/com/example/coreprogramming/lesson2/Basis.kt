package com.example.coreprogramming.lesson2

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
}
