package com.example.hencoder10taglayout;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {
    private List<Rect> mRects = new ArrayList<>();
    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;
        int widthUsed = 0;
        int heightUsed = 0;
        int maxHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < getChildCount(); i++){
            View child = getChildAt(i);
            // 测量子View的尺寸, widthUsed要传0，不然需要折行的View会被缩小
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            if (widthMeasureSpec != MeasureSpec.UNSPECIFIED && (child.getMeasuredWidth() + widthUsed) > MeasureSpec.getSize(widthMeasureSpec)){
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
                widthUsed = 0;
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
                heightUsed+= maxHeight;
                maxHeight = 0;
            }
            Rect rect = getRect(i);
            rect.left = widthUsed;
            rect.top = heightUsed;
            rect.right = widthUsed + child.getMeasuredWidth();
            rect.bottom = heightUsed + child.getMeasuredHeight();
            widthUsed += child.getMeasuredWidth();
            maxWidth = Math.max(maxWidth, widthUsed);
            maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
        }
        setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec), resolveSize(heightUsed, heightMeasureSpec));
    }

    private Rect getRect(int index){
        if (index >= mRects.size()){
           Rect rect =  new Rect();
           mRects.add(rect);
           return rect;
        }
        return mRects.get(index);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() <= 0){
            return;
        }
        for (int i = 0; i < getChildCount(); i++){
            View child = getChildAt(i);
            Rect rect = mRects.get(i);
            child.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MyLayoutParams(super.generateDefaultLayoutParams());
    }

    private static class MyLayoutParams extends MarginLayoutParams{

        public MyLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public MyLayoutParams(int width, int height) {
            super(width, height);
        }

        public MyLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public MyLayoutParams(LayoutParams source) {
            super(source);
        }
    }
}
