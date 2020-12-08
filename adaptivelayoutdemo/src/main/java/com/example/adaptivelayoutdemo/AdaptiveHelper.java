package com.example.adaptivelayoutdemo;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;

public class AdaptiveHelper {
    private int mMinHeight;
    private int mMaxHeight;
    /**
     * 是否开启功能
     */
    private boolean open = true;
    /**
     * 是否需要变短
     */
    private boolean needShorten = false;
    private NestedScrollingParentHelper mHelper;
    private View mTargetView;

    public AdaptiveHelper(ViewGroup root, int maxHeight, int minHeight, boolean needShorten) {
        mMaxHeight = maxHeight;
        mMinHeight = minHeight;
        this.needShorten = needShorten;
        mHelper = new NestedScrollingParentHelper(root);
    }

    public void setMinHeight(int minHeight) {
        mMinHeight = minHeight;
    }

    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && open;
    }


    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    public void onStopNestedScroll(@NonNull View target, int type) {
        mHelper.onStopNestedScroll(target, type);
    }

    // 列表滑完了，剩余的让父View消费
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.w(getClass().getSimpleName(), "onNestedScroll dyUnconsumed = " + dyUnconsumed + " dyConsumed = " + dyConsumed);
        if (mTargetView == null) {
            return;
        }
        // 下滑,等列表到顶后尺寸再变大
        if (dyUnconsumed < 0) {
            ViewGroup.LayoutParams layoutParams = mTargetView.getLayoutParams();
            int height = mTargetView.getMeasuredHeight();
            // 没到最大值，变大
            if (height < mMaxHeight) {
                // 求消费的
                int changedHeight = -Math.min(mMaxHeight - height, Math.abs(dyUnconsumed) * 2);
                Log.w(getClass().getSimpleName(), "onNestedScroll changedHeight = " + changedHeight + " height = " + layoutParams.height);
                layoutParams.height = height - changedHeight;
                mTargetView.setLayoutParams(layoutParams);
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
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.w(getClass().getSimpleName(), "onNestedPreScroll dy = " + dy);
        if (mTargetView == null) {
            return;
        }
        // 下滑
        if (dy <= 0) {
        } else {
            // 上滑，先处理，自己先处理，尺寸变小后再让RV处理
            if (needShorten) {
                ViewGroup.LayoutParams layoutParams = mTargetView.getLayoutParams();
                int height = mTargetView.getMeasuredHeight();
                if (height > mMinHeight) {
                    int increasedHeight = Math.min(height - mMinHeight, Math.abs(dy));
                    consumed[1] = increasedHeight >= dy ? dy : increasedHeight;
                    Log.w(getClass().getSimpleName(), "onNestedPreScroll changedHeight = " + consumed[1] + " height = " + layoutParams.height);
                    layoutParams.height = height - increasedHeight;
                    mTargetView.setLayoutParams(layoutParams);
                }
            }
        }
    }

    public void setTargetView(View targetView) {
        mTargetView = targetView;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void init(){
        if (mTargetView == null){
            return;
        }
        ViewGroup.LayoutParams layoutParams = mTargetView.getLayoutParams();
        layoutParams.height = mMinHeight;
        mTargetView.setLayoutParams(layoutParams);
    }
}
