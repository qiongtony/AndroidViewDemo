package com.example.coordinaotrlayoutdemo.layoutmanager;

import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 支持item不同大小的流式LayoutManager
 * 包含两个过程：
 * 1、初始类似自定义ViewGroup的layout过程，区别是只需要layout一屏幕的View；
 * 2、滑动：这里有两个操作：第一步是把不在屏幕的View的移除掉；
 *                        第二步layout：上滑，从当前最后的子View开始往下顺序的add；
 *                                     下滑，从当前第一个子View开始往上逆序的add；（这里主要的难点是保证和顺序时layout的位置保持一样，当前的处理是用顺序保存的位置来layout逆序的位置）
 */
public class FlowLayoutManager extends RecyclerView.LayoutManager {
    private int mVerticalOffset;// 竖直偏移量
    // 屏幕内第一个可见View的Pos
    private int mFirstVisiblePos;
    // 屏幕内最后一个可见View的pos
    private int mLastVisiblePos;

    // key：View的position，Value为View的bounds
    private SparseArray<Rect> mItemRects = new SparseArray<>();

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
        Log.i("WWS", "onLayoutChildren");
        // 缓存ItemView
        detachAndScrapAttachedViews(recycler);

        // 初始化
        mVerticalOffset = 0;
        mFirstVisiblePos = 0;
        mLastVisiblePos = 0;

        fill(recycler, state);
    }


    /**
     * 填充整屏幕的View：过程和竖直LinearLayout的测量和布局过程是类似的，先measure出子View的尺寸，然后按规则去layout
     * 1、获取子View，测量宽高；
     * 2、布局：
     *                  a、该行排下的话，就往右排，比较得出本行item最高的height，用于转行；
     *                  b、转行：转行后的View的bottom到RV的底了，铺满一屏幕了后面的View不需要测量和布局了
     *                          View没到底，从行头开始layout
     * @param recycler
     * @param state
     */
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
                Rect rect = new Rect(leftOffset, topOffset + mVerticalOffset, leftOffset + getDecoratedMeasurementHorizontal(child), topOffset + mVerticalOffset +  getDecoratedMeasurementVertical(child));
                mItemRects.append(i, rect);

                leftOffset += getDecoratedMeasurementHorizontal(child);
                lineMaxHeight = Math.max(lineMaxHeight, getDecoratedMeasurementVertical(child));
            }else{
                // 当前行排不下
                leftOffset = getPaddingLeft();
                topOffset = topOffset + lineMaxHeight;

                lineMaxHeight = 0;
                // 新起一行，判断有没有到底，到底的话移除View并回收，循环结束
                if (topOffset > getHeight() - getPaddingBottom()){
                    removeAndRecycleView(child, recycler);
                    mLastVisiblePos = i - 1;
                }else {
                    layoutDecoratedWithMargins(child, leftOffset, topOffset, leftOffset + getDecoratedMeasurementHorizontal(child), topOffset + getDecoratedMeasurementVertical(child));
                    Rect rect = new Rect(leftOffset, topOffset + mVerticalOffset, leftOffset + getDecoratedMeasurementHorizontal(child), topOffset + mVerticalOffset +  getDecoratedMeasurementVertical(child));
                    mItemRects.append(i, rect);

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

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    /**
     * 上滑为正值，下滑为负值
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 位移为0或没有子View直接返回
        if (dy == 0 || getChildCount() == 0){
            return 0;
        }
        int realOffset = dy;// 实际滑动的距离,可能会在边界处被修正
        // 下滑时要超过顶部了，此时修正一下需要滑动的距离，不要滑这么多
        if (mVerticalOffset + realOffset < 0){
            realOffset = -mVerticalOffset;
        }else if (realOffset > 0){
            // 下边界
            View lastChild = getChildAt(getChildCount() - 1);
            if (getPosition(lastChild) == getItemCount() -1){
                // 看看最后一个item的底部距离RV底部的距离，
                int gap = getHeight() - getPaddingBottom() - getDecoratedBottom(lastChild);
                // 大于0，说明还要往下移一点
                if (gap > 0){
                    realOffset = -gap;
                }else {
                    // 超了，则取小的保证移动贴底
                    realOffset = Math.min(realOffset, -gap);
                }
            }
        }
        // 先填充，再位移
        realOffset = fill(recycler, state, realOffset);

        // 已滑动的距离
        mVerticalOffset += realOffset;
        // 滑动所有的childView，这个距离是实际滑动的距离
        offsetChildrenVertical(-realOffset);
        return realOffset;
    }

    /**
     * 先实现向底部滑动的逻辑
     * 向底部滑动，顺序填充
     * 向顶部滑动，逆序填充
     * @param recycler
     * @param state
     * @param dy        +底部；-顶部
     * @return
     */
    private int fill(RecyclerView.Recycler recycler, RecyclerView.State state, int dy){
        int topOffset = getPaddingTop();
        // 回收越界子View
        if (getChildCount() > 0){
            // 为什么要从下往上找，而不是从0开始呢？从后面开始remove，前面的index还是保持不变的，从0开始childCount会不断变小
            for (int i = getChildCount() - 1; i >= 0; i--){
                View child =getChildAt(i);
                if (dy > 0){
                    // 顶部离开屏幕的View，回收
                    if (getDecoratedBottom(child) - dy < topOffset){
                        // 该方式快速滑动只会调用少量的itemView,相比起来效率更高
                        // itemView添加到mCachedViews列表内
                        removeAndRecycleView(child, recycler);
                        // 使用该方式createViewHolder的调用次数为itemCount
//                        detachAndScrapView(child, recycler);
                        mFirstVisiblePos++;
                    }
                }else if (dy < 0){
                    // 回收当前屏幕，下越界的View
                    if (getDecoratedTop(child) - dy > getHeight() - getPaddingBottom()){
                        removeAndRecycleView(child, recycler);
//                        detachAndScrapView(child, recycler);
                        mLastVisiblePos--;
                    }
                }
            }
        }

        int leftOffset = getPaddingLeft();
        int lineMaxHeight = 0;
        // layout子View
        Log.i("WWS", "mFirstVisiblePos = " + mFirstVisiblePos + " childCount = " + getChildCount());
        if (dy >= 0){
            int minPos = mFirstVisiblePos;
            mLastVisiblePos = getItemCount() - 1;
            // 上滑，从最后一个可见item开始顺序往下排
            if (getChildCount() > 0){
                View lastView = getChildAt(getChildCount() - 1);
                minPos = getPosition(lastView) + 1; // 从最后一个View+1开始
                // FIXME:这里为什么这么处理，其实就是不从空白开始，从当前最后一个View的rightTop开始layout
                topOffset = getDecoratedTop(lastView);
                leftOffset = getDecoratedRight(lastView);
                lineMaxHeight = Math.max(lineMaxHeight, getDecoratedMeasurementVertical(lastView));

                // 顺序addChildView
                for (int i = minPos; i <= mLastVisiblePos; i++){
                    View child = recycler.getViewForPosition(i);
                    addView(child);
                    measureChildWithMargins(child, 0, 0);
                    if (leftOffset + getDecoratedMeasurementHorizontal(child) <= getHorizontalSpace()){
                        layoutDecoratedWithMargins(child, leftOffset, topOffset, leftOffset + getDecoratedMeasurementHorizontal(child), topOffset + getDecoratedMeasurementVertical(child));

                        Rect rect = new Rect(leftOffset, topOffset + mVerticalOffset, leftOffset + getDecoratedMeasurementHorizontal(child), topOffset + mVerticalOffset +  getDecoratedMeasurementVertical(child));
                        mItemRects.append(i, rect);

                        leftOffset += getDecoratedMeasurementHorizontal(child);
                        lineMaxHeight = Math.max(lineMaxHeight, getDecoratedMeasurementVertical(child));
                    }else{
                        // 当前行排不下，转行
                        leftOffset = getPaddingLeft();
                        topOffset = topOffset + lineMaxHeight;

                        lineMaxHeight = 0;
                        // 判断有没有到底，到底的话移除View并回收，循环结束，如果itemView的高度不一样的话不应该这么判断吧？这个最简单的就是不管，好像也符合直觉
                        if (topOffset > getHeight() - getPaddingBottom()){
                            removeAndRecycleView(child, recycler);
                            mLastVisiblePos = i - 1;
                        }else {
                            layoutDecoratedWithMargins(child, leftOffset, topOffset, leftOffset + getDecoratedMeasurementHorizontal(child), topOffset + getDecoratedMeasurementVertical(child));

                            Rect rect = new Rect(leftOffset, topOffset + mVerticalOffset, leftOffset + getDecoratedMeasurementHorizontal(child), topOffset + mVerticalOffset +  getDecoratedMeasurementVertical(child));
                            mItemRects.append(i, rect);

                            leftOffset += getDecoratedMeasurementHorizontal(child);
                            lineMaxHeight = Math.max(lineMaxHeight, getDecoratedMeasurementVertical(child));
                        }
                    }
                }

                // FIXME:这里不明白是什么意思，已经layout完了，如果最后一个没到底的时候不能再滑下去了
                View lastChild = getChildAt(getChildCount() - 1);
                if (getPosition(lastChild) == getItemCount() -1){
                    int gap = getHeight() - getPaddingBottom() - getDecoratedBottom(lastChild);
                    if (gap > 0){
                        dy -= gap;
                    }
                }
            }
        }else{
            // 下滑，从第一个可见的item，开始逆序addView，不断往前插
                int maxPos = getItemCount() - 1;
                mFirstVisiblePos = 0;
                if (getChildCount() > 0){
                    maxPos = getPosition(getChildAt(0)) - 1;
                }
                for (int i = maxPos; i >= mFirstVisiblePos; i--){
                    Rect rect = mItemRects.get(i);
                    // 到顶了，不用layout了
                    if ((rect.bottom - mVerticalOffset - dy) < getPaddingTop()){
                        mFirstVisiblePos = i + 1;
                        break;
                    }else {
                        View view = recycler.getViewForPosition(i);
                        // 添加到最前面
                        addView(view, 0);
                        measureChildWithMargins(view, 0, 0);
                        layoutDecoratedWithMargins(view, rect.left, rect.top - mVerticalOffset, rect.right, rect.bottom - mVerticalOffset);

                    }
                }
        }
        return dy;
    }
}
