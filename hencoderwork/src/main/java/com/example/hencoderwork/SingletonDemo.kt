package com.example.hencoderwork

import android.util.Log

/**
 * 实现https://kaixue.io/kotlin-basic-2/的作业
 * 主要包含：
 * 1、创建一个无法通过构造器创建的单例；
 * 2、计算array、intArray、list存储[1,100_000]的时间
 */
class SingletonDemo {

    private constructor(

    )

    companion object {
        fun getInstance() = SingletonDemo()
        const val age = 100
    }

    fun print(){
        // int<包装类型<list
        // 14ms
        ArrayTime()
        // 5ms
        IntArrayTime()
        // 20
        ListTime()
    }

    fun ArrayTime(){
        val startTime = System.currentTimeMillis()
        val array = arrayOfNulls<Int>(100_000)
        var avg : Long = 0
        for (i in 1..100_000){
            array[i - 1] = (i);
            avg += array[i - 1]!!
        }
        Log.i("WWS", "array print avg = $avg consumeTime = ${System.currentTimeMillis() - startTime}")
    }

    fun IntArrayTime(){
        val startTime = System.currentTimeMillis()
        var array = IntArray(100_000)
        var avg : Long = 0
        for (i in 1..100_000){
            array[i - 1] = i;
            avg += array[i - 1]
        }
        Log.i("WWS", "intArray print avg = $avg consumeTime = ${System.currentTimeMillis() - startTime}")
    }

    fun ListTime(){
        val startTime = System.currentTimeMillis()
        var list = mutableListOf<Int>()
        var avg : Long = 0
        for (i in 1..100_000){
            list.add(i)
            avg += list[i - 1]
        }
        Log.i("WWS", "list print avg = $avg consumeTime = ${System.currentTimeMillis() - startTime}")

    }
}