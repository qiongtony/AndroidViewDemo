package com.example.coordinaotrlayoutdemo.layoutmanager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 支持item不同大小的流式LayoutManager
 */
public class FlowLayoutManager extends RecyclerView.LayoutManager {
    private int mVerticalOffset;// 竖直偏移量
    // 屏幕内第一个可见View的Pos
    private int mFirstVisiblePos;
    // 屏幕内最后一个可见View的pos
    private int mLastVisiblePos;

    @Override
    public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }

    /**
     * 在初始化或数据源改变时会调用
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0){
            detachAndScrapAttachedViews(recycler);
            return;
        }
        // isPreLayout返回true，表示支持动画
        if (getChildCount() == 0 && state.isPreLayout()){
            return;
        }
        // 缓存ItemView
        detachAndScrapAttachedViews(recycler);

        // 初始化
        mVerticalOffset = 0;
        mFirstVisiblePos = 0;
        mLastVisiblePos = 0;

        fill(recycler, state);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state){
        int topOffset = getPaddingTop();
        int leftOffset = getPaddingLeft();
        // 每行的最大高度，换行时确定换行的高度
        int lineMaxHeight = 0;
        int minPos = mFirstVisiblePos;
        // 先测试，所以全部都展示
        mLastVisiblePos = getItemCount() - 1;
        for (int i = minPos; i <= mLastVisiblePos; i++){
            // 取出ItemView，可能是从缓存或onCreateView获取的
            View child = recycler.getViewForPosition(i);
            addView(child);
            measureChildWithMargins(child, 0, 0);

            // 当前行可以排下
            if (leftOffset + getDecoratedMeasurementHorizontal(child) <= getHorizontalSpace()){
                layoutDecoratedWithMargins(child, leftOffset, topOffset, leftOffset + getDecoratedMeasurementHorizontal(child), topOffset + getDecoratedMeasurementVertical(child));

                leftOffset += getDecoratedMeasurementHorizontal(child);
                lineMaxHeight = Math.max(lineMaxHeight, getDecoratedMeasurementVertical(child));
            }else{
                // 当前行排不下
                leftOffset = getPaddingLeft();
                topOffset = topOffset + lineMaxHeight;

                lineMaxHeight = 0;
                // 新起一行，判断有没有到底，到底的话移除View并回收
                if (topOffset > getHeight() - getPaddingBottom()){
                    removeAndRecycleView(child, recycler);
                    mLastVisiblePos = i - 1;
                }else {
                    layoutDecoratedWithMargins(child, leftOffset, topOffset, leftOffset + getDecoratedMeasurementHorizontal(child), topOffset + getDecoratedMeasurementVertical(child));

                    leftOffset += getDecoratedMeasurementHorizontal(child);
                    lineMaxHeight = Math.max(lineMaxHeight, getDecoratedMeasurementVertical(child));
                }
            }
        }
    }

    private int getDecoratedMeasurementHorizontal(View child){
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        return getDecoratedMeasuredWidth(child) + params.leftMargin + params.rightMargin;
    }

    private int getDecoratedMeasurementVertical(View child){
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        return getDecoratedMeasuredHeight(child) + params.topMargin + params.bottomMargin;
    }

    private int getHorizontalSpace(){
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int getVerticalSpace(){
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }
}
