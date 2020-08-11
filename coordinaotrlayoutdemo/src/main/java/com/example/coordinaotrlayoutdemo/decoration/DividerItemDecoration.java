package com.example.coordinaotrlayoutdemo.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 绘制分割线，支持横向和竖向两种方向，支持自定义颜色
 * 不确定是否要支持的：分割线的高度、分割线偏移量
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private DisplayMetrics dm;

    public DividerItemDecoration(Context context){
        this(context, Color.GRAY);
    }

    public DividerItemDecoration(Context context, int color){
        dm = context.getResources().getDisplayMetrics();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
    }

    /**
     * 在item绘制前绘制，在item内容下方
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }


    /**
     * 在item绘制后绘制，在item内容上方
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int orientation;
        if (parent.getLayoutManager() instanceof LinearLayoutManager){
            orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        }else{
            throw new IllegalStateException("layoutManager must be LinearLayoutManager!");
        }
        // 从第二item开始绘制，在头尾不绘制
        Log.i("WWS", "onDrawOver childCount = " + parent.getChildCount() + " orientation = " + orientation);
        for(int i = 1; i < parent.getChildCount(); i++){
            View childView = parent.getChildAt(i);
            if (orientation == LinearLayoutManager.VERTICAL) {
                c.drawRect(childView.getLeft(), childView.getTop() - dm.density * 1, childView.getRight(), childView.getTop(), paint);
            }else{
                c.drawRect(childView.getLeft() - dm.density * 1, childView.getTop(), childView.getLeft(), childView.getBottom(), paint);
            }
        }
    }

    /**
     * 用来进行偏移，相当于把item的范围扩大，其实可以理解为扩大的是分割线的长度，这样就不会影响item原始的尺寸了
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int orientation;
        if (layoutManager instanceof LinearLayoutManager){
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            // 竖向，底部增加一个间距
            if (orientation == LinearLayoutManager.VERTICAL){
                outRect.bottom = (int) (dm.density * 1);
            }else {
                // 横向，右边增加一个间距
                outRect.right = (int) (dm.density * 1);
            }
        }else{
            throw new IllegalStateException("layoutManager must be LinearLayoutManager!");
        }
        Log.i("WWS", "getItemOffsets  orientation = " + orientation);
    }
}
