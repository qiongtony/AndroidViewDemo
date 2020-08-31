package com.example.coordinaotrlayoutdemo.layoutmanager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coordinaotrlayoutdemo.util.ScreenUtil;

/**
 * 自动给底部填充空白内容保证所有的item的高度不小于RV高度
 */
public class BottomSpaceLayoutManager extends LinearLayoutManager {
    private RecyclerView.Adapter mAdapter;
    // 头部需要忽略的Item的数量，通过这个可以不包括头部View的高度
    private int mHeaderItemCount = 0;
    private SparseIntArray mHeightArray = new SparseIntArray();
    public BottomSpaceLayoutManager(Context context) {
        super(context);
    }

    public BottomSpaceLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public BottomSpaceLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void attachAdapter(@NonNull RecyclerView.Adapter adapter){
        this.mAdapter = adapter;
    }
    @Override
    public void measureChildWithMargins(@NonNull View child, int widthUsed, int heightUsed) {
        super.measureChildWithMargins(child, widthUsed, heightUsed);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        int position = layoutParams.getViewLayoutPosition();
        mHeightArray.put(position, child.getMeasuredHeight());
        int bottomMargin = 0;
        if (position == mAdapter.getItemCount() - 1){
            bottomMargin = getHeight();
            for (int i = 0; i < mHeightArray.size(); i++){
                int key = mHeightArray.keyAt(i);
                if (key >= mHeaderItemCount && key < mAdapter.getItemCount()){
                    int height = mHeightArray.get(key);
                    bottomMargin = Math.max(0, bottomMargin - height);
                    if (bottomMargin == 0){
                        break;
                    }
                }
            }
        }
        Log.i("WWS", "bottomMargin = " + bottomMargin);
        layoutParams.bottomMargin = bottomMargin;
    }

    public void setHeaderItemCount(int headerItemCount) {
        mHeaderItemCount = headerItemCount;
    }
}
