package com.example.autoscrolllistdemo;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

public class AutoPollRecyclerView extends RecyclerView {
    private static final long TIME_AUTO_POLL = 16;
    // 是否正在轮询
    private boolean running;
    // 是否可以自动轮询
    private boolean canRun;
    private AutoPollTask mAutoPollTask;
    private boolean test = false;
    // 存储已播放动画item的下标，保证动画一轮每个item只执行一次
    private SparseIntArray mShowArray;
    private SparseIntArray mHideArray;

    public AutoPollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mShowArray = new SparseIntArray();
        mHideArray = new SparseIntArray();
        mAutoPollTask = new AutoPollTask(this);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
//        Log.w("RV", "WWS onScrolled dx = " + dx + " dy = " + dy);
        if (test) {
            return;
        }
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int lastCompletelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != NO_POSITION && mShowArray.indexOfKey(lastVisibleItemPosition) < 0) {
                Log.w(getClass().getSimpleName(), "AAAA lastVisibleItemPosition = " + lastVisibleItemPosition);
                final View view = linearLayoutManager.findViewByPosition(lastVisibleItemPosition);
                if (view != null) {
                    mShowArray.put(lastVisibleItemPosition, lastVisibleItemPosition);
                    view.setAlpha(0.5f);
                    view.animate().alpha(1f).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).setDuration(1500).start();
                }
            }
            if (
                    // 除第一个位置，设alpha后会导致firstCompletelyVisibleItemPosition是-1，所以只需要判断firstVisibleItemPosition
                    (firstVisibleItemPosition > 0 && firstCompletelyVisibleItemPosition == NO_POSITION) ||
                    (firstVisibleItemPosition != NO_POSITION && firstCompletelyVisibleItemPosition != NO_POSITION && mHideArray.indexOfKey(firstVisibleItemPosition) < 0 && firstVisibleItemPosition != firstCompletelyVisibleItemPosition)){
                final View view = linearLayoutManager.findViewByPosition(firstVisibleItemPosition);
                if (view != null) {
                    mHideArray.put(firstVisibleItemPosition, firstVisibleItemPosition);
                    view.setAlpha(0.5f);
                    view.animate().alpha(0f).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
//                            view.setAlpha(1f);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).setDuration(1500L).start();
                }
            }
            Log.w(getClass().getSimpleName(), "onScrolled lastCompletelyVisibleItemPosition = " + lastCompletelyVisibleItemPosition + " lastVisibleItemPosition = " + lastVisibleItemPosition + "" +
                    " firstVisibleItemPosition = " + firstVisibleItemPosition + " itemCount = " + linearLayoutManager.getItemCount() + " getScrollVertical = " + canScrollVertically(1));
            if (!canScrollVertically(1)) {
                clearAnimationArray();
                scrollToPosition(0);
            }
            /*if (lastCompletelyVisibleItemPosition == NO_POSITION || (lastCompletelyVisibleItemPosition == linearLayoutManager.getItemCount() - 1)){
                if (firstVisibleItemPosition == lastVisibleItemPosition  && (lastVisibleItemPosition == linearLayoutManager.getItemCount() - 1)){
                    scrollToPosition(0);
                }
                return;
            }*/
        }
    }

    private void clearAnimationArray() {
        mShowArray.clear();
        mHideArray.clear();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        Log.w("RV", "WWS onScrollStateChanged state = " + state);
    }

    public void start() {
        start(true);
    }

    /**
     * 开始循环滚动
     *
     * @param init 是否初始化，从头开始，false的话就从停止点继续
     */
    public void start(boolean init) {
        if (test) {
            return;
        }
        if (running) {
            stop();
        }
        canRun = true;
        running = true;
        // 初始化
        if (init) {
            clearAnimationArray();
            scrollTo(0, 0);
        }
        postDelayed(mAutoPollTask, TIME_AUTO_POLL);
    }

    public void stop() {
        running = false;
        removeCallbacks(mAutoPollTask);
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent e) {
        // 不可触摸
        return false;
    }*/

    static class AutoPollTask implements Runnable {
        private final WeakReference<AutoPollRecyclerView> mReference;

        public AutoPollTask(AutoPollRecyclerView recyclerView) {
            mReference = new WeakReference<>(recyclerView);
        }

        @Override
        public void run() {
            AutoPollRecyclerView recyclerView = mReference.get();
            if (recyclerView == null) {
                return;
            }
            if (recyclerView.running && recyclerView.canRun) {
                recyclerView.scrollBy(0, 4);
                recyclerView.postDelayed(recyclerView.mAutoPollTask, recyclerView.TIME_AUTO_POLL);

            }
        }
    }


}
