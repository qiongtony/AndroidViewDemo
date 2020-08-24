package com.example.nestedscrolldemo.snaphelper;

import android.content.IntentFilter;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

/**
 * 不带Fling的滑动
 * findSnap->calculateDistanceToFinalSnap->smoothScroll(...)
 *
 * 带Fling的滑动：
 * findTargetSnapPosition->LinearSmoothScroller.start(...)->LinearSmoothScroller.onTargetFound(View,...)->calculateDistanceToFinalSnap->Action.update(...)
 *
 */
public class GallerySnapHelper extends SnapHelper {
    private static final float INVALID_DISTANCE = 1f;
    // fling时滑过的最大item数：3个，防止一滑，滑到底的情况
    private static final int MAX_FLING_SIZE = 3;
    // 每像素滑动需要的时间：通过该值控制Fling时的快慢
    private static final float MILLISECONDS_PER_INCH = 25f;
    private OrientationHelper mHelper;
    private RecyclerView mRecyclerView;
    public GallerySnapHelper(){
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        mRecyclerView = recyclerView;
        super.attachToRecyclerView(recyclerView);
    }

    // 找当前显示内容下需要定位的itemView
    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        Log.i("WWS", "findSnapView");
        return findStartView(layoutManager, getHorizontalHelper(layoutManager));
    }

    private View findStartView(RecyclerView.LayoutManager layoutManager, OrientationHelper orientationHelper){
        Log.i("WWS", "findStartView");
        if (layoutManager instanceof LinearLayoutManager){
            // 找到第一个可见的
            int firstChildPos = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            // 如果没找到，返回空
            if (firstChildPos == RecyclerView.NO_POSITION){
                return null;
            }
            // 如果已经滑倒最后面了，就不用重定位了
            if (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() == (layoutManager.getItemCount() - 1)){
                return null;
            }
            // 如果第一个View显示超过了一半，就以它来定位，否则以下一个item来定位
            View firstChildView = layoutManager.findViewByPosition(firstChildPos);
            if (orientationHelper.getDecoratedEnd(firstChildView) >= orientationHelper.getDecoratedMeasurement(firstChildView) / 2
                //这个条件好像没必要，能看到，肯定是正的吧
//                && mHelper.getDecoratedEnd(firstChildView) > 0
            ){
                return firstChildView;
            }else{
                return layoutManager.findViewByPosition(firstChildPos + 1);
            }
        }
        return null;
    }

    /**
     * 找到要移的ItemView了，计算定位方式的距离
     * @param layoutManager
     * @param targetView
     * @return
     */
    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
        }
        return out;
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager){
        if (mHelper != null){
            return mHelper;
        }
        mHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        return mHelper;
    }

    // itemView距离起始位置的距离
    private int distanceToStart(View targetView, OrientationHelper helper){
        Log.i("WWS", "distanceToStart start = " + (helper.getDecoratedStart(targetView)) + " startAfterPadding = " + helper.getStartAfterPadding());
        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

    @Nullable
    @Override
    protected RecyclerView.SmoothScroller createScroller(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (mRecyclerView == null){
            return null;
        }
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return null;
        }
        return new LinearSmoothScroller(mRecyclerView.getContext()) {
            @Override
            protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
                int[] snapDistances = calculateDistanceToFinalSnap(mRecyclerView.getLayoutManager(), targetView);
                final int dx = snapDistances[0];
                final int dy = snapDistances[1];
                final int time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
                if (time > 0) {
                    action.update(dx, dy, time, mDecelerateInterpolator);
                }
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };
    }

    /**
     * Fling情况下找出停止后ItemView的pos
     * 1、找到当前情况下定位到的ItemView；
     * 2、计算Fling会移动几个item，这样就算出最终定位的ItemView的位置了
     * Fling停下来的时候没有回调吗？
     * @param layoutManager
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)){
            return RecyclerView.NO_POSITION;
        }
        final int itemCount = layoutManager.getItemCount();
        if (itemCount == 0){
            return RecyclerView.NO_POSITION;
        }
        final View currentView = findSnapView(layoutManager);
        if (currentView == null){
            return RecyclerView.NO_POSITION;
        }
        final int currentPos = layoutManager.getPosition(currentView);
        if (currentPos == RecyclerView.NO_POSITION){
            return RecyclerView.NO_POSITION;
        }
        RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider = (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;
        PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
        if (vectorForEnd == null){
            return RecyclerView.NO_POSITION;
        }
        // 算出fling会惯性滑动多少个item（用item的平均宽度算)
        int deltaJump;
        if (layoutManager.canScrollHorizontally()){
            deltaJump = estimateNextPositionDiffForFling(layoutManager, getHorizontalHelper(layoutManager), velocityX, 0);
        }else{
            deltaJump = 0;
        }
        if (deltaJump == 0){
            return RecyclerView.NO_POSITION;
        }
        Log.i("WWS", "deltaJump = " + deltaJump);
        if (deltaJump > 0 && deltaJump >= MAX_FLING_SIZE){
            deltaJump = MAX_FLING_SIZE;
        } else if (deltaJump < 0 && deltaJump <= (-MAX_FLING_SIZE)) {
            deltaJump = -MAX_FLING_SIZE;
        }
        int targetPos = currentPos + deltaJump;
        if (targetPos < 0){
            targetPos = 0;
        }
        if (targetPos >= layoutManager.getItemCount()){
            targetPos = layoutManager.getItemCount() - 1;
        }

        return targetPos;
    }

    private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager layoutManager,
                                                 OrientationHelper helper, int velocityX, int velocityY) {
        int[] distances = calculateScrollDistance(velocityX, velocityY);
        float distancePerChild = computeDistancePerChild(layoutManager, helper);
        if (distancePerChild <= 0) {
            return 0;
        }
        if (distances[0] > 0){
            return (int) Math.floor(distances[0] / distancePerChild);
        }else {
            return (int) Math.round(distances[0] / distancePerChild);
        }
    }

    private float computeDistancePerChild(RecyclerView.LayoutManager layoutManager,
                                          OrientationHelper helper) {
        View minPosView = null;
        View maxPosView = null;
        int minPos = Integer.MAX_VALUE;
        int maxPos = Integer.MIN_VALUE;
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return INVALID_DISTANCE;
        }

        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            final int pos = layoutManager.getPosition(child);
            if (pos == RecyclerView.NO_POSITION) {
                continue;
            }
            if (pos < minPos) {
                minPos = pos;
                minPosView = child;
            }
            if (pos > maxPos) {
                maxPos = pos;
                maxPosView = child;
            }
        }
        if (minPosView == null || maxPosView == null) {
            return INVALID_DISTANCE;
        }
        int start = Math.min(helper.getDecoratedStart(minPosView),
                helper.getDecoratedStart(maxPosView));
        int end = Math.max(helper.getDecoratedEnd(minPosView),
                helper.getDecoratedEnd(maxPosView));
        int distance = end - start;
        if (distance == 0) {
            return INVALID_DISTANCE;
        }
        return 1f * distance / ((maxPos - minPos) + 1);
    }
}
