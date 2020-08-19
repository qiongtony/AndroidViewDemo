package com.example.coordinaotrlayoutdemo.nestscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

public class NestedListView extends ListView implements NestedScrollingChild2 {
    private NestedScrollingChildHelper mHelper;
    private int mLastY;
    private int mOldTop;
    private int[] mScrollConsumed;
    private int[] mScrollOffset;

    public NestedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setNestedScrollingEnabled(true);
    }

    {
        mHelper = new NestedScrollingChildHelper(this);
        mScrollConsumed = new int[2];
        mScrollOffset = new int[2];
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return mHelper.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        mHelper.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return mHelper.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return mHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return mHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mHelper.isNestedScrollingEnabled();
    }

    // 通过onTouchEvent去分发及消耗滑动
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
       switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:{
                mLastY = (int) ev.getY();
                mOldTop = getTop();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH);
                break;
            }
           case MotionEvent.ACTION_UP:
           case MotionEvent.ACTION_CANCEL:
               stopNestedScroll(ViewCompat.TYPE_TOUCH);
               break;
           case MotionEvent.ACTION_MOVE:
             int tranlationY = (int) (mLastY - ev.getY());
             mLastY = (int) ev.getY();
             // 先给父View看看是不是消费，父View消费完，自己在消费，自己消费不够，再给父View
               if (this.dispatchNestedPreScroll(0, tranlationY, mScrollConsumed, mScrollOffset, ViewCompat.TYPE_TOUCH)){
                   ev.offsetLocation(0, tranlationY - mScrollConsumed[1]);
                   return true;
               }else{
                   // 下滑，先自己消费，剩余的给父View消费
                   if (tranlationY < 0){
                       int delta = Math.max(tranlationY, -getActualScrollY());
                       ev.offsetLocation(0, delta);
                       Log.i("WWS", "子View先滑动 delta = " + delta + " tranlationY = " + tranlationY);
                       dispatchNestedScroll(0, delta, 0,tranlationY - delta, mScrollOffset, ViewCompat.TYPE_TOUCH);
                       if (delta >= 0){
                           return true;
                       }
                   }
               }
            break;

        };
        return super.onTouchEvent(ev);
    }

    public int getActualScrollY() {
        View c = getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight() ;
    }
}
