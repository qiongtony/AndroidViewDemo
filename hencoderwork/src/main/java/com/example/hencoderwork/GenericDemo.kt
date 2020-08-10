package com.example.hencoderwork

/**
 * Kotlin泛型作业，文章地址：https://kaixue.io/kotlin-generics/
 * 实现一个 fill 函数，传入一个 Array 和一个对象，将对象填充到 Array 中，要求 Array 参数的泛型支持逆变（假设 Array size 为 1）。
 * 实现一个 copy 函数，传入两个 Array 参数，将一个 Array 中的元素复制到另外个 Array 中，要求 Array 参数的泛型分别支持协变和逆变
 */
class GenericDemo<T>{
    fun fill(array: Array<in T>, t : T){
        array[0] = t
    }

    //
    fun copy(array1 : Array<T>, array2 : Array<T>){
        for (i in array2.indices){
            array1[i] = array2[i]
        }
    }
}