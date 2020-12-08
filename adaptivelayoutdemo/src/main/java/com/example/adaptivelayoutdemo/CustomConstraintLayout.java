package com.example.adaptivelayoutdemo;

import android.content.Context;
import android.content.res.TypedArray;
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
    private int mTargetId;

    {
        mAdaptiveHelper = new AdaptiveHelper(this, TARGET_MAX_HEIGHT, TARGET_MIN_HEIGHT, false);
    }

    public CustomConstraintLayout(@NonNull Context context) {
        super(context);
    }

    public CustomConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomConstraintLayout);
        mTargetId = ta.getResourceId(R.styleable.CustomConstraintLayout_ccl_targetId, -1);
        ta.recycle();
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
        if (mTargetId == -1){
            return;
        }
        mAdaptiveHelper.setTargetView(findViewById(mTargetId));
    }

    // 列表滑完了，剩余的让父View消费
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.w(getClass().getSimpleName(), "onNestedScroll dyUnconsumed = " + dyUnconsumed + " dyConsumed = " + dyConsumed);
        if (mTargetId == -1){
            return;
        }
        mAdaptiveHelper.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    // 列表还没开始滑，父View根据需求判断需不需要滑，并求出剩余子View可滑的距离
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.w(getClass().getSimpleName(), "onNestedPreScroll dy = " + dy);
        if (mTargetId == -1){
            return;
        }
        mAdaptiveHelper.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    public void init(){
        mAdaptiveHelper.init();
    }
}
