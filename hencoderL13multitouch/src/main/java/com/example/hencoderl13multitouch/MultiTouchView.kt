package com.example.hencoderl13multitouch

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.hencoderl13multitouch.util.BitmapUtil
import javax.security.auth.login.LoginException

/**
 * 支持多点触摸移动的ImageView
 * 多点触摸逻辑：
 * 接力型：
 * 新的手指按下，则屏蔽其他的手指，以该手指的移动为准
 * 手指抬起时，如果不是最大index的那个手指，则将接收事件序列的手指设为index最大的那个手指（即为手指数-1）；
 *            如果是，则将接收事件序列的手指，则将接收事件序列的手指设为倒数第二个手指(即为手指数-2)
 * 触摸事件序列的一些基础知识：
 * 不是以某个手指的action作为触发条件，而是View收到新的事件
 * ACTION_DOWN/MOVE，针对的是整个View，而不是单指某个手指按下/移动了，在事件中可以获取所有手指的位置
 * 每个手指包含下标和id两个概念：
 *                              下标是顺序且会复用的（例如三个手指按下，下标分别为0、1、2，第二个手指弹起，此时第三个手指下标变为1，也可以理解下标的最大值=手指数-1，第二个手指按下时
 *                              第三个手指的index又变回了2
 *                              而id是不变的（如三个手指如下，此时id分别为0、1、2，第二个手指弹起，另外两个id不变.第二个手指又按下时，第二个手指的index和id又变回，1，1,由于想要id复用，index也复用了）
 */
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

    // 当前跟踪手指的id，用id是因为不会边，index会被复用---id也会被复用吧
    var trackingPointerId : Int = 0
    init{
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        bitmap = BitmapUtil.getBitmap(getContext())


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                val actionIndex = event.findPointerIndex(trackingPointerId)
                startX = event.getX(actionIndex)
                startY = event.getY(actionIndex)
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {
                val actionIndex = event.findPointerIndex(trackingPointerId)
                offsetX = originalOffsetX + event.getX(actionIndex) - startX
                offsetY = originalOffsetY + event.getY(actionIndex) - startY
                invalidate()
            }

            // 多指触控逻辑：接力型
            MotionEvent.ACTION_POINTER_DOWN -> {
                trackingPointerId = event.getPointerId(event.actionIndex)
                Log.i("WWS", "ACTION_POINTER_DOWN index = ${event.actionIndex} trackingPointerId = $trackingPointerId")
                startX = event.getX(event.actionIndex)
                startY = event.getY(event.actionIndex)
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_POINTER_UP -> {
                val actionUpIndex = event.actionIndex
                var tracingIndex = 0
                if (actionUpIndex == (event.pointerCount - 1)){
                    tracingIndex = event.pointerCount - 2
                }else{
                    tracingIndex = event.pointerCount - 1
                }
                trackingPointerId = event.getPointerId(tracingIndex)
                startX = event.getX(trackingPointerId)
                startY = event.getY(trackingPointerId)
                originalOffsetX = offsetX
                originalOffsetY = offsetY
                Log.i("WWS", "tracingIndex = ${tracingIndex} trackingPointerId = ${trackingPointerId} startX = $startX" +
                        " startY = $startY")
            }
        }
        // 必须返回true，不然可能接收不到事件序列
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, offsetX, offsetY, paint)
    }
}