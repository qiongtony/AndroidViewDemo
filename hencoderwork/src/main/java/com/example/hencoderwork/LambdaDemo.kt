package com.example.hencoderwork

import android.util.Log
import android.view.View

/**
 * Lambda和匿名函数的本质：函数对象，用起来和对象没什么区别
 */
class LambdaDemo {
    fun test1(view : View){
        view.setOnClickListener(fun(_: View) {
            Log.i("WWS", "setOnClickListener")
        })

        view.setOnClickListener {
            v : View ->
            Log.i("WWS", "简化的Lambda形式")
        }
    }

    fun test2() : String{
        val b : (Int) -> String = {
            // lambda的最后一行就是返回值
            it.toString()
        }
        return "${b}哈哈哈";
    }

}