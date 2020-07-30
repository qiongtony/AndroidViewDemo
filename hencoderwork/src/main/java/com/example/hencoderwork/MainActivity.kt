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
