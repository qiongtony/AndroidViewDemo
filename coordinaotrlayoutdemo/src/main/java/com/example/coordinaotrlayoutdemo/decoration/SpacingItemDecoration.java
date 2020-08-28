package com.example.coordinaotrlayoutdemo.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * itemWidth = (rv.width - rv.paddingLeft - rv.paddingRight) / spanCount - decoration.left - decoration.right
 * 那怎么能让itemWidth在加了decoration后还能保持一致呢
 * 规则：贴边的item，贴边的那一侧不能有间距，就是说第一个的左侧不能有间距，最后一列的右侧不能有间距；
 * 而间距=当前项.right+下一项.left
 * 试了一下不用啊，整体只要上一个的右边加下一个的左边这个值固定就行了，而左右贴边的距离定，上面的值就是间距-贴边距离了
 * 链接：https://stackoverflow.com/questions/28531996/android-recyclerview-gridlayoutmanager-column-spacing
 */
public class SpacingItemDecoration extends RecyclerView.ItemDecoration{
    private int mSpace;
    private int spanCount = 3;
    private boolean includeEdge = false;
    public SpacingItemDecoration(int space, boolean includeEdge) {
        mSpace = space;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int viewPos = parent.getChildLayoutPosition(view);
        int column = (parent.getChildAdapterPosition(view)) % spanCount;// 计算这个child 处于第几列
        if (includeEdge){
            outRect.left = mSpace - column * mSpace / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column) * mSpace / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
        }else{
            outRect.left = (column * mSpace / spanCount);// column * ((1f / spanCount) * spacing)
            outRect.right = mSpace - (column) * mSpace / spanCount;// spacing - (column + 1) * ((1f /    spanCount) * spacing)

        }
    }
}
