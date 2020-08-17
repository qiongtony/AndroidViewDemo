package com.example.hencoderwork

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class CoroutineDemo {
    fun test1() {
        // 方式一：使用runBlocking顶层函数，试用场景：单元测试---线程阻塞
        runBlocking {
            getImage("first way")
        }

        // 方式二，使用GlobalScope单例对象，调用launch开启协程。Android内不推荐使用，1、单例（容易存在内存泄漏；2、无法取消
        GlobalScope.launch {
            getImage("second way")
        }

        // 主要用第三种方式！方式三，通过CoroutineContext创建一个CoroutineScope对象，通过Scope对象开启协程
        CoroutineScope(Dispatchers.Main).launch {
            getImage("third way")
        }
    }

    suspend fun getImage(imageUrl: String) = withContext(Dispatchers.IO) {
        // 。。。。
        Log.i("WWS", "threadName = ${Thread.currentThread().name} imgUrl = ${imageUrl}")
    }

    // 加载图片并显示
    fun test2(imageView: ImageView) {
        // 指定主线程为Main,每次在其他线程执行完后会自动切回Main线程
        CoroutineScope(Dispatchers.Main).launch {
            // 工作线程下载图片，并返回bitmap
            val image = getImage(imageView, "http://images.xuejuzi.cn/1612/1_161227195259_1.jpg")
            // 主线程显示
            imageView.setImageBitmap(image)
        }
    }

    suspend fun getImage(imageView: ImageView, imageUrl: String) = withContext(Dispatchers.IO) {
        Log.i("WWS", "threadName = ${Thread.currentThread().name} imgUrl = ${imageUrl}")
        val result = Glide.with(imageView).asBitmap().load(imageUrl);
        result.submit().get();
    }
}