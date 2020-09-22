package com.example.customlayoutmanagerdemo;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

public class CenterPagerSnapHelper extends SnapHelper {
    private static final float INVALID_DISTANCE = 1f;
    // fling时滑过的最大item数：3个，防止一滑，滑到底的情况
    private static final int MAX_FLING_SIZE = 3;
    // 每像素滑动需要的时间：通过该值控制Fling时的快慢
    private static final float MILLISECONDS_PER_INCH = 25f;
    private OrientationHelper mHelper;
    private RecyclerView mRecyclerView;


    public CenterPagerSnapHelper() {
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager){
        if (mHelper != null){
            return mHelper;
        }
        mHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        return mHelper;
    }


    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        mRecyclerView = recyclerView;
        super.attachToRecyclerView(recyclerView);
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        return new int[0];
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        return null;
    }


    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        return 0;
    }


}
