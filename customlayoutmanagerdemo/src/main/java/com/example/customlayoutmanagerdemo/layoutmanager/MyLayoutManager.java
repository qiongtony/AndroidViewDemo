package com.example.customlayoutmanagerdemo.layoutmanager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customlayoutmanagerdemo.ItemConfigure;

public class MyLayoutManager extends LinearLayoutManager {

    private int mScrollOffset;

    public MyLayoutManager(Context context) {
        super(context);
    }

    public MyLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        updateAnimation(recycler);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int consumeX = super.scrollHorizontallyBy(dx, recycler, state);
        mScrollOffset += consumeX;
        updateAnimation(recycler);
        return consumeX;
    }

    private void updateAnimation(RecyclerView.Recycler recycler) {
        for (int i = 0; i < getChildCount();i++){
            View child = getChildAt(i);
            if (child != null) {
                child.setPivotY(child.getHeight() / 2);
                child.setPivotX(child.getWidth() / 2);
                child.setScaleX(ItemConfigure.getDefault().getMinScale());
                child.setScaleY(ItemConfigure.getDefault().getMinScale());
            }
        }
        int targetPos = (int) Math.floor(mScrollOffset / getOneScreenWidth());
        float scale = mScrollOffset % getOneScreenWidth() * 1f / getOneScreenWidth();

        Log.i("WWS", "mScrollOffset = " + mScrollOffset + " targetPos = " + targetPos + " scale = " + scale);

        for (int i = -1; i < 2; i++) {
            if (i == 0) {
                updateItemScale(recycler, targetPos + i, 1f - scale);
            } else {
                updateItemScale(recycler, targetPos + i, scale);
            }
        }
    }

    private void updateItemScale(RecyclerView.Recycler recycler, int pos, float scale) {
        if (pos < 0 || pos >= getItemCount()) {
            return;
        }
        float actualScale = ItemConfigure.getDefault().getMinScale() + ItemConfigure.getDefault().getScaleValue() * scale;
        View child = findViewByPosition(pos);
        if (child == null) {
            return;
        }
        child.setPivotY(child.getHeight() / 2);
        child.setPivotX(child.getWidth() / 2);
        child.setScaleX(actualScale);
        child.setScaleY(actualScale);
    }

    private int getOneScreenWidth() {
        return ItemConfigure.getDefault().getOneScreenWidth();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        // 因为float值有时没办法刚好到1.0，而可能是0.9999，所以要在停下来的时候修正一下
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            int pos = findFirstCompletelyVisibleItemPosition();
            // 找不到就不改了，不然会出现其他问题
            if (pos == RecyclerView.NO_POSITION){
                return;
            }
            mScrollOffset = pos * getOneScreenWidth();
            updateAnimation(null);
        }
    }
}
