package com.example.coreprogramming.lesson2

class CountryApp {
    // 函数作为参数
    fun filterCountries(countries : List<Country>, test : (Country) -> Boolean) : List<Country>{
        val res = mutableListOf<Country>()
        for (c in countries){
            // 通过传入的函数来判断是否符合条件
            if (test(c)){
                res.add(c)
            }
        }
        return res
    }
}