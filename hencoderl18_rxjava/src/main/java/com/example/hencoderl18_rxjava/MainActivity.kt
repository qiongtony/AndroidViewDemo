package com.example.hencoderl18_rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var remainingDuration = 6500L
        val duration = remainingDuration
        Log.e("WWS", "countDown init = " + remainingDuration);
        Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .take(duration / 1000 + 1)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    remainingDuration = duration - it * 1000
                    Observable.just(remainingDuration)
                }.subscribe {
                   Log.e("WWS", "countDown = " + it +  " threadName = " + Thread.currentThread().name);
                }
        val test  = Test()
        test.internal()
//        test.singleTest()
    }
}
