package com.example.coreprogramming.lesson2

fun  main(args : Array<String>){
    val basis = Basis()
    basis.testFunVar()
    basis.testCurry()
}

/**
 * 函数总结：
 * 带花括号：普通的代码块函数
 * 带等号：普通的单表达式函数
 * 两者都有：Lambda表达式，实际是构造了一个对象
 */
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

        // 更简便的方式，使用匿名函数实现，匿名函数实际是对象
        countryApp.filterCountries(countries, fun (country : Country) : Boolean{
            return country.continient == "EU" && country.population > 10000
        })

        // 使用lambda表达式简化
        countryApp.filterCountries(countries) {
            country -> country.continient == "EU" && country.population > 1000
        }

        // 套一个lambda表达式的话不会打印任何值
        listOf(1, 2, 3).forEach {
            // 这里其实是返回的lambda的对象 (Int)->Unit
//            foo2(it)
            // 所以如果要调用方法，需要在调用一下该lambda对象的invoke方法，这时候就会执行了
//            foo2(it).invoke()

            // lambda对象的invoke方法也可以直接简写成"()"
//            foo2(it)()
        }
    }

    fun foo2(int : Int) =
            {
        print(int)
    }

    fun testCurry(){
        fun sum1(x : Int) : (Int) -> Int{
            return { y : Int -> x + y}
        }
        fun sum(x : Int) = {
            y : Int ->{
            z : Int -> x + y + z
        }
        }
        println(sum(1)(2)(3))

        fun omitParentheses(block : () -> Unit){
            block()
        }

        omitParentheses {
            println("parentheses is omitted")
        }
    }


}
