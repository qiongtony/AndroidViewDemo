package com.example.adaptivelayoutdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;

/**
 *
 */
public class CustomConstraintLayout extends ConstraintLayout implements
        // 用NestedScrollingParent3,有两个onNestedScroll，长的那个才生效？
        NestedScrollingParent2 {
    private NestedScrollingParentHelper mHelper;
    private int TARGET_MIN_HEIGHT = ScreenUtils.dp2px(300);
    private int TARGET_MAX_HEIGHT = ScreenUtils.dp2px(400);

    {
        mHelper = new NestedScrollingParentHelper(this);
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

    // 列表滑完了，剩余的让父View消费
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.w(getClass().getSimpleName(), "onNestedScroll dyUnconsumed = " + dyUnconsumed);
        // 下滑,等列表到顶后尺寸再变大
        if (dyUnconsumed <= 0) {
            View containerView = findViewById(R.id.container_view);
            ViewGroup.LayoutParams layoutParams = containerView.getLayoutParams();
            int height = containerView.getMeasuredHeight();
            // 没到最大值，变大
            if (height < TARGET_MAX_HEIGHT) {
                // 求消费的
                int changedHeight = -Math.min(TARGET_MAX_HEIGHT - height, Math.abs(dyUnconsumed) * 2);
                layoutParams.height = height - changedHeight;
                containerView.setLayoutParams(layoutParams);
            }
        } else {
            /*View containerView = findViewById(R.id.container_view);
            ViewGroup.LayoutParams layoutParams = containerView.getLayoutParams();
            int height = containerView.getMeasuredHeight();
            if (height > TARGET_MIN_HEIGHT) {
                int increasedHeight = Math.min(height - TARGET_MIN_HEIGHT, Math.abs(dyUnconsumed));
                layoutParams.height = height - increasedHeight;
                containerView.setLayoutParams(layoutParams);
            }*/
        }
    }

    // 列表还没开始滑，父View根据需求判断需不需要滑，并求出剩余子View可滑的距离
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.w(getClass().getSimpleName(), "onNestedPreScroll dy = " + dy);
        // 下滑
        if (dy <= 0) {
           /* View containerView = findViewById(R.id.container_view);
            ViewGroup.LayoutParams layoutParams = containerView.getLayoutParams();
            int height = containerView.getMeasuredHeight();
            // 没到最大值，变大
            if (height < TARGET_MAX_HEIGHT){
                // 求消费的
                consumed[1] = -Math.min(TARGET_MAX_HEIGHT - height, Math.abs(dy));
                layoutParams.height = height - consumed[1];
                containerView.setLayoutParams(layoutParams);
            }*/
        } else {
            // 上滑，先处理，自己先处理，尺寸变小后再让RV处理
            View containerView = findViewById(R.id.container_view);
            ViewGroup.LayoutParams layoutParams = containerView.getLayoutParams();
            int height = containerView.getMeasuredHeight();
            if (height > TARGET_MIN_HEIGHT) {
                int increasedHeight = Math.min(height - TARGET_MIN_HEIGHT, Math.abs(dy) * 2);
                consumed[1] = increasedHeight >= dy ? dy : increasedHeight;
                layoutParams.height = height - increasedHeight;
                containerView.setLayoutParams(layoutParams);
            }
        }
    }
}
