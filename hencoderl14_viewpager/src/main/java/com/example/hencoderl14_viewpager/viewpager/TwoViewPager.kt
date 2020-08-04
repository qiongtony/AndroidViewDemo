package com.example.hencoderl14_viewpager.viewpager

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.MotionEvent.*
import android.widget.OverScroller
import com.example.hencoderl14_viewpager.util.ScreenUtil

class TwoViewPager(context : Context, attrs : AttributeSet) : ViewGroup(context, attrs) {
    var downX : Float = 0f
    var downY : Float = 0f

    var scrolling : Boolean = false

    var currentIndex = 0

    val viewConfiguration : ViewConfiguration = ViewConfiguration.get(getContext())

    var originalScrollX = 0

    val velocityTracker : VelocityTracker = VelocityTracker.obtain()

    val overScroller : OverScroller = OverScroller(getContext())

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount){
            val child = getChildAt(i)
            var left = 0
            var right = 0
            var top = 0
            var bottom = 0
            left = i * ScreenUtil.screenWidth()
            right = left + ScreenUtil.screenWidth()
            top = 0
            bottom = ScreenUtil.screenHeight()
            child.layout(left, top, right, bottom)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var result : Boolean = false
        when(ev?.actionMasked){
            ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
                scrolling = false
                velocityTracker.clear()
            }

            ACTION_MOVE -> {
                // 判断是否满足滑动条件
                if (Math.abs(ev.x - downX) >= viewConfiguration.scaledPagingTouchSlop){
                    scrolling = true
                    parent.requestDisallowInterceptTouchEvent(true)
                    result = true
                }
            }
        }
        return result || super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        velocityTracker.addMovement(event)
        when(event?.actionMasked){
            ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                scrolling = false
                velocityTracker.clear()
            }

            ACTION_MOVE -> {
                // scroll值是反的
                val scrollOffsetX = (downX - event.x).toInt()

                // 判断边界情况，在左右边界，就不能往“外”滑动了
                val needOffset = if (currentIndex == 0 && scrollOffsetX < 0) return false else if (currentIndex == 1 && scrollOffsetX > 0) return false else true
                if (needOffset) {
                    scrollTo(originalScrollX + scrollOffsetX, 0)
                }else{
                    scrollTo(originalScrollX, 0)
                }
                invalidate()
            }

            ACTION_UP -> {
                scrolling = false
                // 大于半页，就翻页
                val scrollOffsetX = (downX - event.x).toInt()
                Log.i("WWS", "currentIndex  = ${currentIndex} scrollOffsetX = $scrollOffsetX needWidth = ${ScreenUtil.screenWidth() / 2f}")
                if (currentIndex == 0 && scrollOffsetX >= (ScreenUtil.screenWidth() / 2f)){
                    scrollTo(originalScrollX + ScreenUtil.screenWidth(), 0)
                    currentIndex = 1
                    originalScrollX = ScreenUtil.screenWidth()
                }else if (currentIndex == 1 && scrollOffsetX <= (ScreenUtil.screenWidth() / -2f)){
                    scrollTo(originalScrollX - ScreenUtil.screenWidth(), 0)
                    originalScrollX = - ScreenUtil.screenWidth()
                    currentIndex = 0
                } else if (Math.abs(scrollOffsetX) <= (ScreenUtil.screenWidth() / 2f)){
                    scrollTo(originalScrollX, 0)
                }


                velocityTracker.computeCurrentVelocity(1000, viewConfiguration.scaledMaximumFlingVelocity.toFloat())
                postInvalidateOnAnimation()
                invalidate()
            }
        }
        return true
    }


}