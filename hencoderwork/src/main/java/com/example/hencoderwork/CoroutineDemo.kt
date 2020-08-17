package com.example.hencoderwork

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import java.util.ArrayList

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

    /**
     * 协程挂起
     * 不是suspend实现了挂起，而是suspend内的方法实现了挂起(如Dispatchers.Main，挂起并切换到Main线程;或delay，挂起，并定时切回原线程）
     * 非阻塞式挂起：切线程，耗时操作放到子线程去执行了，执行完在切回Main线程执行相应逻辑
     */
    fun test3(imageView: ImageView, imageView2: ImageView){
        Log.i("WWS", "协程挂起前的代码....")
        CoroutineScope(Dispatchers.Main).launch {
            // 1、在IO线程
            val bitmap = getImage(imageView, "http://images.xuejuzi.cn/1612/1_161227195259_1.jpg")
            // 在Main线程
            Log.i("WWS", "getImage 挂起了 threadName = " + Thread.currentThread().name)
            // 在IO线程
            val foruthBitmapList = splitImageWithFourth(bitmap, 4)
            // 在Main线程
            Log.i("WWS", "foruthBitmapList 挂起了")
            imageView.setImageBitmap(foruthBitmapList.get(0));

            val ninethBitmapList = splitImageWithFourth(bitmap, 9)
            Log.i("WWS", "ninethBitmapList 挂起了")
            imageView2.setImageBitmap(ninethBitmapList.last())
        }
        Log.i("WWS", "协程挂起后的代码....")
    }

    /**
     * 将一个bitmap，裁剪成n份，每份宽度一样，高度一样，显示的内容不断往上
     */
    suspend fun splitImageWithFourth(bitmap : Bitmap, count : Int) : ArrayList<Bitmap> = withContext(Dispatchers.IO){
        Log.i("WWS", "splitImageWithFourth threadName = ${Thread.currentThread().name} count = $count")
        val bitmapList = ArrayList<Bitmap>()
        val rawWidth = bitmap.width;
        val rawHeight = bitmap.height
        val perHeight = rawHeight / count;
        for (i in 0 until count){
            // 第一个参数：待切割的bitmap；
            // 第二/三参数：切割的开始位置
            // 第三/四参数：切割的大小
            val temp = Bitmap.createBitmap(bitmap, 0, perHeight * i, rawWidth, perHeight)
            bitmapList.add(temp)
        }
         bitmapList

    }
}