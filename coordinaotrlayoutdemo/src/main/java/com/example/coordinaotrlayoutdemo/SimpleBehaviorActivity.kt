package com.example.coordinaotrlayoutdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button

/**
 * 简单的自定义Behavior效果演示，
 * TextView随着Button一起移动
 */
class SimpleBehaviorActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_behavior)
        findViewById<Button>(R.id.btn).setOnTouchListener(this)
    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.actionMasked == MotionEvent.ACTION_MOVE){
            v?.x = event.rawX - (v?.width?.div(2) ?: 0)
            v?.y = event.rawY - (v?.height ?: 0) / 2
        }
        return true
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}