package com.example.hencoderl18_rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val test  = Test()
        Observable.interval(1, TimeUnit.SECONDS).subscribe { t: Long? -> {
            Log.i("WWS", "value = ${t}")
        } }
    }
}
