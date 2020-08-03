package com.example.hencoderl14_viewpager

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import androidx.core.util.forEach
import androidx.core.util.isEmpty
import com.example.hencoderl14_viewpager.util.ScreenUtil

/**
 * 多点触控：
 * 互相独立型
 *
 * 特点：每个手指各自处理，以绘制直线为例，当该手指抬起时绘制内容清除
 * 一个手指怎么绘制呢？记录下按下点，然后依次移动画一条线
 * 多个手指如何举一反三呢？就是多个“一个手指”嘛，只是要注意POINTER_UP的时候要清除该手指绘制的内容，POINTER_DOWN记下开始位置就可以了
 * 下标需要使用id进行记录
 */
class MultiTouchView2(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val pointerPaths: SparseArray<Path>

    init {
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        paint.strokeWidth = ScreenUtil.dp2Px(3)
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
        pointerPaths = SparseArray()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            ACTION_DOWN -> {
                var path: Path
                if (pointerPaths.get(0) != null) {
                    path = pointerPaths.get(0)
                } else {
                    path = Path()
                    pointerPaths.put(0, path)
                }
                path.reset()
                path.moveTo(event.x, event.y)
            }

            ACTION_POINTER_DOWN -> {
                var path: Path
                // 记下当前手指id，以及按下位置
                var actionId = event.getPointerId(event.actionIndex)
                if (pointerPaths.get(actionId) != null) {
                    path = pointerPaths.get(actionId)
                } else {
                    path = Path()
                    pointerPaths.put(actionId, path)
                }
                path.reset()
                path.moveTo(event.getX(event.actionIndex), event.getY(event.actionIndex))

                invalidate()
            }

            ACTION_MOVE -> {
                // 遍历存储的path，画线
                for (i in 0 until pointerPaths.size()) {
                    var key = pointerPaths.keyAt(i)
                    var tempValue = pointerPaths.get(key)
                    var tempIndex = event.findPointerIndex(key)
                    tempValue.lineTo(event.getX(tempIndex), event.getY(tempIndex))
                }
                invalidate()
            }

            ACTION_POINTER_UP -> {
                // 清除抬起手指的path
                var pointerUpId = event.getPointerId(event.actionIndex)
                pointerPaths.remove(pointerUpId)
                invalidate()
            }

            ACTION_UP -> {
                pointerPaths.clear()
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (pointerPaths.isEmpty()) {
            return;
        }
        pointerPaths.forEach { key, value ->
            canvas?.drawPath(value, paint)
        }
    }
}