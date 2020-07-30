// 注解指定类名，用于生成顶层函数的类名
@file:JvmName("StringFunctions")
package com.example.kotlinleanlib

import java.awt.Button
import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import java.util.*
import javax.swing.text.View
import kotlin.collections.HashSet

/**
 * 自定义打印list
 */
fun <T> joinToString(collection : Collection<T>,
                    separator: String = "，",// 设置默认值
                     prefix: String = "",
                     postfix : String = ""
                     ) : String{
    val result = StringBuilder(prefix)
    for ((index, element) in collection.withIndex()){
        // 除第一个元素外添加分隔符
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

// 扩展函数
// String的扩展函数，lastChar是新定义的方法，String是final类，所以是使用Wrapper实现咯？
// 实际是静态函数，第一个参数是接收者对象
fun String.lastChar() : Char = this.get(this.length - 1)


// 最终版本，为Collection添加扩展函数
fun<T> Collection<T>.joinToStringFinal(
        separator: String = "， ",
        prefix: String = "",
        postfix: String = ""
) : String{
    val result = StringBuilder(prefix)

    for ((index, element) in this.withIndex()){
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun Stack<String>.test() = println("I'm a stack!")

fun Vector<String>.test() = println("I'm a vector")

// 扩展属性
// 声明一个扩展属性
val String.lastChar: Char
    get() = get(length - 1)

var StringBuilder.lastChar: Char
    // getter属性
    get() = get(length - 1)
    // setter属性
    set(value : Char){
        setCharAt(length - 1, value)
    }

// 可变参数，传入数组时需要用"*"展开
fun variableParams(args : Array<String>){
    val list = listOf("args : ", * args)
    println(list)
}

// 使用分解函数，获取全路径的目录名、文件名和扩展名
fun stringSplit(){
    val str = "/Users/yole/kotlin-book/chapter.adoc"
    // 目录
    val directory = str.substringBeforeLast("/")
    val fullName = str.substringAfterLast("/")

    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")

    println("Dir : $directory, name： $fileName, ext：$extension")
}

// 局部函数

class User(val id : Int, val name: String, val address: String)

fun saveUser(user : User){
    // 声明了一个局部函数
    fun validate(
    value : String,
    fieldName : String){
        if (value.isEmpty()){
            throw IllegalArgumentException("Can't save user ${user.id}: empty $fieldName")
        }
    }

    validate(user.name, "Name")
    validate(user.address, "Address")

    println("save user success!")
}