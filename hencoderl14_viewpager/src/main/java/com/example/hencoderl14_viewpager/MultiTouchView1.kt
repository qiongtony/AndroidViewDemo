package com.example.hencoderl14_viewpager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import com.example.hencoderl14_viewpager.util.BitmapUtil
import java.util.jar.Attributes

/**
 * 多点协作型：
 * 根据按下的手指数，算出当前的“焦点”---所有点的中心点(求出数值和/点数)
 */
class MultiTouchView1(context: Context?, attrs: AttributeSet?) : View(context,attrs){
    val paint : Paint;
    val bitmap : Bitmap;
    var offsetX : Float = 0f
    var offsetY : Float = 0f

    var downX : Float = 0f
    var downY : Float = 0f

    // 抬起时记下当前的偏移
    var originalX : Float = 0f
    var originalY : Float = 0f
    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        bitmap = BitmapUtil.getBitmap(getContext())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y

                originalX = offsetX
                originalY = offsetY
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                var (sumX: Float, sumY: Float) = getFocusPoint(event)
                downX = sumX / event.pointerCount
                downY = sumY / event.pointerCount
                originalX = offsetX
                originalY = offsetY
                invalidate()
            }

            MotionEvent.ACTION_POINTER_UP -> {
                val upId = event.actionIndex
                var sumX : Float = 0f
                var sumY : Float = 0f
                // 这里需要去掉弹起的那个手指
                for (i in 0 until event.pointerCount){
                    if (i == upId){
                        continue
                    }
                    sumX += event.getX(i)
                    sumY += event.getY(i)
                }
                downX = sumX / (event.pointerCount - 1)
                downY = sumY / (event.pointerCount - 1)
                originalX = offsetX
                originalY = offsetY
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                var (sumX: Float, sumY: Float) = getFocusPoint(event)
                invalidate()
                offsetX = originalX + sumX / event.pointerCount - downX
                offsetY = originalY + sumY / event.pointerCount - downY
                invalidate()
            }
        }

        return true
    }

    private fun getFocusPoint(event: MotionEvent): Pair<Float, Float> {
        var sumX: Float = 0f
        var sumY: Float = 0f
        for (i in 0 until event.pointerCount) {
            sumX += event.getX(i)
            sumY += event.getY(i)
        }
        return Pair(sumX, sumY)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, offsetX, offsetY, paint)
    }
}