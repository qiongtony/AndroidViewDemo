package com.example.coreprogramming.lesson5

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

open class GenericsToken <T>{
    var type : Type = Any::class.java
    init {
        val superClass = this.javaClass.genericSuperclass
        type = (superClass as ParameterizedType).actualTypeArguments[0]
    }
}