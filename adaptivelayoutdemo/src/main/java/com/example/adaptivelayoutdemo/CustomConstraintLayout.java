package com.example.adaptivelayoutdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.NestedScrollingParent2;

/**
 *
 */
public class CustomConstraintLayout extends ConstraintLayout implements
        // 用NestedScrollingParent3,有两个onNestedScroll，长的那个才生效？
        NestedScrollingParent2 {
    private int TARGET_MIN_HEIGHT = ScreenUtils.dp2px(238);
    private int TARGET_MAX_HEIGHT = ScreenUtils.dp2px(432);
    private AdaptiveHelper mAdaptiveHelper;

    {
        mAdaptiveHelper = new AdaptiveHelper(this, TARGET_MAX_HEIGHT, false);
    }

    public CustomConstraintLayout(@NonNull Context context) {
        super(context);
    }

    public CustomConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /*@Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        Log.w(getClass().getSimpleName(), "onNestedScrollLong dyUnconsumed = " + dyUnconsumed);
    }*/

    // 竖向滑动才处理嵌套滑动
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return mAdaptiveHelper.onStartNestedScroll(child, target, axes, type);
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mAdaptiveHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mAdaptiveHelper.onStopNestedScroll(target, type);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mAdaptiveHelper.setTargetView(findViewById(R.id.container_view));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mAdaptiveHelper.setTargetView(findViewById(R.id.container_view));
    }

    // 列表滑完了，剩余的让父View消费
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.w(getClass().getSimpleName(), "onNestedScroll dyUnconsumed = " + dyUnconsumed + " dyConsumed = " + dyConsumed);
        mAdaptiveHelper.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    // 列表还没开始滑，父View根据需求判断需不需要滑，并求出剩余子View可滑的距离
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.w(getClass().getSimpleName(), "onNestedPreScroll dy = " + dy);
        mAdaptiveHelper.onNestedPreScroll(target, dx, dy, consumed, type);
    }
}
