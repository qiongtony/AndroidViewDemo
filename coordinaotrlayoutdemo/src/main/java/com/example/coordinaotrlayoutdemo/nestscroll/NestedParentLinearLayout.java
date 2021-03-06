package com.example.coordinaotrlayoutdemo.nestscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;

public class NestedParentLinearLayout extends LinearLayout implements NestedScrollingParent2 {
    private NestedScrollingParentHelper mHelper;
    public NestedParentLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    {
        mHelper = new NestedScrollingParentHelper(this);
    }

    // 竖向滑动才处理嵌套滑动
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
       mHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mHelper.onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (dyUnconsumed == 0){
            return;
        }
        int scrollY = getScrollY();
        //下滑且到顶就不滑了
        if (dyUnconsumed < 0 && scrollY <= 0) {
            return;
        }
        int targetTop = target.getTop();
        int canConsumeY = 0;
        if (scrollY < targetTop && dyUnconsumed > 0 && scrollY >= 0){
            // 求出可消费的滑动距离，顶部滑过了就不再消费了
            canConsumeY = Math.min(dyUnconsumed, targetTop - scrollY);
        }
        //  下滑，消费滑动距离，直到scrollY为0
        else if (dyUnconsumed < 0 && target.getScrollY() == 0){
            canConsumeY = Math.max(dyUnconsumed, -scrollY);
        }
        Log.i("WWS", "onNestedScroll dyUnconsumed = " + dyUnconsumed + " canConsumeY = " + canConsumeY + " targetTop = " + targetTop + " scrollY = " +scrollY);
        scrollBy(0, canConsumeY);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        int targetTop = target.getTop();
        int targetBottom = target.getBottom();
        int scrollY = getScrollY();
        // 没有到底部，且上滑，这时候自己处理滑动
        if (scrollY < targetTop && dy > 0 && scrollY >= 0){
            consumed[0] = 0;
            // 求出可消费的滑动距离，顶部滑过了就不再消费了
            consumed[1] = Math.min(dy, targetTop - scrollY);
        }
        // 下滑，消费滑动距离，直到scrollY为0
//        else if (dy < 0 && target.getScrollY() == 0){
//            consumed[1] = Math.max(dy, -scrollY);
//        }
        // 下滑不消费，让子View先滑
        else if (dy < 0){
            consumed[1] = 0;
        }
        scrollBy(0, consumed[1]);
        Log.i("WWS", "Parent onNestedPreScroll consumedY = " + consumed[1] + " targetBottom = " + targetBottom) ;
    }
}
