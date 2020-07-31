package com.example.hencoderl13multitouch

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.hencoderl13multitouch.util.BitmapUtil

class MultiTouchView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint:Paint
    val bitmap : Bitmap
    // 偏移值
    var offsetX : Float = 0f
    var offsetY : Float = 0f

    // 本次事件序列按下的起点
    var startX : Float = 0f
    var startY : Float = 0f
    // 记录上次抬起时的初始偏移值
    var originalOffsetX : Float = 0f
    var originalOffsetY : Float = 0f
    init{
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        bitmap = BitmapUtil.getBitmap(getContext())


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {
                offsetX = originalOffsetX + event.x - startX
                offsetY = originalOffsetY + event.y - startY
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, offsetX, offsetY, paint)
    }
}