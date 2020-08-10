package com.example.hencoderwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    lateinit var view : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view = findViewById(R.id.view)
        printViewId(view)

        SingletonDemo.getInstance().print()

       val user =  User("吴伟山", 100, "广东")
        // 位置参数应该在第一个命名参数之前，后面的参数都必须用命名参数
        user.sayHi("haha", age = 100, test = 10)

        var student = Student("吴哈哈", 1, "0班")
        student.show()

        val genericDemo = GenericDemo<Int>()
        var array1  = arrayOfNulls<Int>(1)

        genericDemo.fill(array1, 10)
        Log.i("WWS", "fill array1 = ${array1[0]}")

        val array2  = arrayOf(1, 3, 5)
        var array3 = arrayOf(2, 4, 6)
        genericDemo.copy(array2, array3)
        for (i in array2.indices){
            Log.i("WWS", "copy array2[$i] = ${array2[i]}")
        }
    }

    fun printViewId(view : View?){
        Log.i("WWS", "viewId = ${view?.id}")
    }

    // val与java的"final"基本一致，只是可以在getter方法动态修改而已；
    val size : Int
        get(){
            return 1;
        }
}
